package com.serverless;

import com.jrestless.aws.gateway.GatewayFeature;
import com.jrestless.aws.gateway.handler.GatewayRequestObjectHandler;
import org.glassfish.jersey.server.ResourceConfig;

/* copied from https://github.com/bbilger/jrestless#aws-usage-example */
public class Handler extends GatewayRequestObjectHandler {
  public Handler() {
    ResourceConfig resourceConfig = new ResourceConfig().register(GatewayFeature.class).packages("com.serverless");
    init(resourceConfig);
    start();
  }
}
