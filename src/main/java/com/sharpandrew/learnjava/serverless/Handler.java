package com.sharpandrew.learnjava.serverless;

import com.jrestless.aws.gateway.GatewayFeature;
import com.jrestless.aws.gateway.handler.GatewayRequestObjectHandler;
import com.sharpandrew.learnjava.GraphResource;
import com.sharpandrew.learnjava.UserResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.bridge.SLF4JBridgeHandler;

public final class Handler extends GatewayRequestObjectHandler {
  // https://github.com/bbilger/jrestless#aws-usage-example
  public Handler() {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
    ResourceConfig resourceConfig = new ResourceConfig()
        .registerInstances(UserResource.create())
        .registerInstances(GraphResource.create())
        .register(GatewayFeature.class);
    init(resourceConfig);
    start();
  }
}