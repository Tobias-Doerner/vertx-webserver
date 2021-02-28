package de.spdns.datanode.webserver;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class HttpServerVerticle extends AbstractVerticle {

    private HttpServer server;

    @Override
    public void start(Promise<Void> startPromise) {
        final Router router = Router.router(vertx);
        router
            .route("/*")
            .handler(StaticHandler.create()
                .setWebRoot(config().getString("http.webroot.directory", "webroot"))
                .setCachingEnabled(true)
                .setFilesReadOnly(true)
            );

        server = vertx.createHttpServer(
            new HttpServerOptions()
                .setCompressionLevel(config().getInteger("http.compression.level", 5))
                .setCompressionSupported(config().getBoolean("http.compression.supported", true))
                .setDecompressionSupported(config().getBoolean("http.decompression.supported", true))
                .setPort(config().getInteger("http.port", 8080))
                .setHost(config().getString("http.address", "0.0.0.0"))
        );

        server.requestHandler(router).listen(ar -> {
            if (ar.succeeded()) {
                startPromise.complete();
            } else {
                startPromise.fail(ar.cause());
            }
        });
    }

    @Override
    public void stop(Promise<Void> stopPromise) {
        server.close(ar -> {
            if (ar.succeeded()) {
                stopPromise.complete();
            } else {
                stopPromise.fail(ar.cause());
            }
        });
    }
}
