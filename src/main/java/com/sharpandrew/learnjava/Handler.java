package com.sharpandrew.learnjava;

import com.jrestless.aws.gateway.GatewayFeature;
import com.jrestless.aws.gateway.handler.GatewayRequestObjectHandler;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class Handler extends GatewayRequestObjectHandler {
  /** copied from https://github.com/bbilger/jrestless#aws-usage-example */
  public Handler() {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
    ResourceConfig resourceConfig = new ResourceConfig()
        .register(GatewayFeature.class)
        .packages("com.sharpandrew.learnjava");
    init(resourceConfig);
    start();
  }
}
