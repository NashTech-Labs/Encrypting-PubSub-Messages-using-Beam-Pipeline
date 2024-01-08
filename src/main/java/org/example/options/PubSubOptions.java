package org.example.options;

import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.Validation;

public interface PubSubOptions extends PipelineOptions {

    @Description("The Cloud Pub/Sub topic to read from.")
    @Validation.Required
    @Default.String("your-topic-id")
    String getInputTopic();

    void setInputTopic(String value);

    @Description("The cloud Pub/Sub subscription to read from")
    @Validation.Required
    @Default.String("your-subscription-id")
    String getInputSubscription();

    void setInputSubscription(String inputSubscription);

    @Description("Whether to use topic or Pub/Sub")
    @Validation.Required
    @Default.Boolean(true)
    Boolean getUseSubscription();

    void setUseSubscription(Boolean useSubscription);


}
