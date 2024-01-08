package org.example.options;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.storage.Storage;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.Validation;
import org.example.services.GcsClientFactory;

public interface BigQueryOptions extends PubSubOptions {
    @Description("GCP project to access")
    @Validation.Required
    @Default.String("your-project-id")
    String getGcpProject();

    void setGcpProject(String gcpProject);

    @Description("GCS Bucket Name")
    @Default.String("gcp-bucket-name")
    String getGcsBucketName();

    void setGcsBucketName(String gcsBucketName);

    @Description("GCS File Name")
    @Default.String("gcs-file-name")
    String getGcsFileName();

    void setGcsFileName(String gcsFileName);

    @JsonIgnore
    @Description("GCS Client")
    @Default.InstanceFactory(GcsClientFactory.class)
    Storage getGcsClient();

    void setGcsClient(Storage gcsClient);
}
