# Vert.x static Webserver

Webserver build for deployment of static content with [Vert.x Web](https://vertx.io/docs/vertx-web/java/#_serving_static_resources)

## Build
Fat Jar can be build with maven
```
mvn clean package
```

## Deployment
For starting the fat jar you need at least Java 11 or higher.
Keep in mind that you must change the working directory to the target where
the fat Jar lies, before you start the jar in your shell.
```
java -jar vertx-webserver-0.0.1-fat.jar
```

## Configuration
The config parameters in the resources/config.json can be overwritten by environment variables, system variables or the parameters set in the target/config.json file.
