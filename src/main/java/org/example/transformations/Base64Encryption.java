package org.example.transformations;

import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Base64;

public class Base64Encryption extends PTransform<PCollection<PubsubMessage>, PCollection<String>> {

    final Logger logger = LoggerFactory.getLogger(Base64Encryption.class);

    @Override
    public PCollection<String> expand(PCollection<PubsubMessage> input) {
        return input.apply("Encrypt", ParDo.of(new DoFn<PubsubMessage, String>() {
            @ProcessElement
            public void process(ProcessContext processContext, PipelineOptions options) throws GeneralSecurityException, IOException {
                PubsubMessage pubsubMessage = processContext.element();
                String messages = new String(pubsubMessage.getPayload(), StandardCharsets.ISO_8859_1);
                logger.info("The message is : {}", messages);
                String base64EncodedMessage = Arrays.toString(Base64.getEncoder().encode(messages.getBytes()));
                processContext.output(base64EncodedMessage);
            }
        }));
    }
}
