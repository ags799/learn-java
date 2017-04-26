package com.serverless;

import com.jrestless.aws.gateway.GatewayFeature;
import com.jrestless.aws.gateway.handler.GatewayRequestAndLambdaContext;
import com.jrestless.aws.gateway.handler.GatewayRequestObjectHandler;
import com.jrestless.core.container.io.JRestlessContainerRequest;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class Handler extends GatewayRequestObjectHandler {
  private static final Logger log = LoggerFactory.getLogger(Handler.class);

  /** copied from https://github.com/bbilger/jrestless#aws-usage-example */
  public Handler() {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
    ResourceConfig resourceConfig = new ResourceConfig()
        .register(GatewayFeature.class)
        .packages("com.serverless");
    init(resourceConfig);
    start();
  }

  @Override
  protected void beforeHandleRequest(GatewayRequestAndLambdaContext request, JRestlessContainerRequest containerRequest) {
    log.error("beforeHandleRequest");
  }
}
