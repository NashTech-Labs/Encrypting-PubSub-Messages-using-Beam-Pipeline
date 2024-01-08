package org.example;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;
import org.example.options.BigQueryOptions;
import org.example.options.BigQueryPipelineFactory;
import org.example.transformations.Base64Encryption;
import org.example.transformations.SendtoGCS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    final static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Creating BigQuery Pipeline");
        Pipeline pipeline = BigQueryPipelineFactory.createBigQueryPipeline(args);
        BigQueryOptions options = pipeline.getOptions().as(BigQueryOptions.class);

        PCollection<@UnknownKeyFor @NonNull @Initialized PubsubMessage> messages;

        if (options.getUseSubscription()) {
            logger.info("Reading From Subscription");
            messages = pipeline.apply("Reading From Subscription", PubsubIO.readMessagesWithAttributes().fromSubscription(options.getInputSubscription()));
        } else {
            logger.info("Reading From Pub Sub Topic");
            messages = pipeline
                    // 1) Read string messages from a Pub/Sub topic.
                    .apply("Read PubSub Messages From Topic", PubsubIO.readMessagesWithAttributes().fromTopic(options.getInputTopic()));
        }

        PCollection<String> encryptedMessages = messages.apply("Encrypt the Messages", new Base64Encryption());
        encryptedMessages.apply("Write to GCS bucket", ParDo.of(new SendtoGCS()));
        pipeline.run();
    }
}