# Splunk Integration for e-com Backend

## Option 1: Splunk Universal Forwarder (recommended)

This is the safest, free, and most reliable local Splunk Enterprise ingestion path.

1. Keep the current JSON rolling file appender in `src/main/resources/logback-spring.xml`.
2. Ensure your application writes logs to `logs/app.log` under the application working directory, or set `LOG_PATH` to a specific absolute directory.
3. Configure the Splunk Universal Forwarder to monitor the log file and rotated archives.
4. Use `sourcetype = _json` so Splunk parses the event as JSON.
5. Do not log sensitive fields such as `password` or `jwt`.

### Recommended UF monitor stanza

Use absolute paths. For example, if your application is installed at `/Users/himanshishrivas/Documents/e-com`:

```
[monitor:///Users/himanshishrivas/Documents/e-com/logs/app.log]
index = main
sourcetype = _json
source = e-com

[monitor:///Users/himanshishrivas/Documents/e-com/logs/app-*.log.gz]
index = main
sourcetype = _json
source = e-com
```

If you set a custom `LOG_PATH`, point the monitor stanza to that directory instead.

### Recommended UF inputs.conf placement

Add the stanza into:

- `$SPLUNK_HOME/etc/system/local/inputs.conf`

or into a custom app under:

- `$SPLUNK_HOME/etc/apps/<your_app>/local/inputs.conf`

### Splunk Enterprise indexer setup

1. Make sure the forwarder is connected to your local Splunk indexer.
2. Confirm the `main` index exists, or replace `index = main` with your own index.
3. Search on `sourcetype=_json source=e-com` in Splunk Web.

### Notes

- For local development, the Splunk Universal Forwarder is free and the preferred way to ingest local application logs.
- Use `disabled = false` only for the monitor inputs you want active.
- If you need host metadata, add `host = my-hostname` to the monitor stanza.

## Option 2: Direct Splunk HEC ingestion via Logback HTTP appender

This requires the Splunk Java logging library or manual JAR installation. The current project does not include the library in Maven Central, so use this only after adding the dependency manually.

### Sample application properties

```properties
splunk.hec.url=https://localhost:8088
splunk.hec.token=YOUR_HEC_TOKEN_HERE
splunk.hec.index=main
splunk.hec.source=e-com
splunk.hec.sourcetype=_json
```

### Sample Logback appender

```xml
<appender name="SPLUNK_HEC" class="com.splunk.logging.HttpEventCollectorLogbackAppender">
    <url>${splunk.hec.url}</url>
    <token>${splunk.hec.token}</token>
    <index>${splunk.hec.index:-main}</index>
    <source>${splunk.hec.source:-e-com}</source>
    <sourcetype>${splunk.hec.sourcetype:-_json}</sourcetype>
    <disableCertificateValidation>true</disableCertificateValidation>
    <layout class="net.logstash.logback.layout.LogstashLayout"/>
</appender>

<root level="INFO">
    <appender-ref ref="CONSOLE" />
    <appender-ref ref="FILE" />
    <appender-ref ref="SPLUNK_HEC" />
</root>
```

### Notes
- The `HTTP Event Collector` token must be created in Splunk with `index` write permission.
- Use TLS and valid certificates in production.
- If the HEC appender class is unavailable, fallback to Universal Forwarder.

## Recommended production flow

1. Log structured JSON to local rolling files.
2. Run Splunk Universal Forwarder to ingest those logs.
3. Use Splunk props/transforms or `sourcetype=_json` to parse fields.
4. Keep HEC as an optional direct ingestion path for future environments.
