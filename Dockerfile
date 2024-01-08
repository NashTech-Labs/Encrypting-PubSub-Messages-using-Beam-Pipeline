FROM gcr.io/dataflow-templates-base/java11-template-launcher-base:latest

ENV FLEX_TEMPLATE_JAVA_MAIN_CLASS=org.example.Application
ENV FLEX_TEMPLATE_JAVA_CLASSPATH=/template/*
ENV JAVA_TOOL_OPTIONS="-Djava.util.logging.config.file=/resources/logback.xml"
WORKDIR .
COPY /target/Encrypting-PubSub-Messages-uisng-Beam-Pipeline.jar /template/