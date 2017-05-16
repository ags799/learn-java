package com.sharpandrew.learnjava.serverless;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.jrestless.aws.gateway.GatewayFeature;
import com.jrestless.aws.gateway.handler.GatewayRequestObjectHandler;
import com.sharpandrew.learnjava.graph.GraphResource;
import com.sharpandrew.learnjava.graph.SearchResource;
import com.sharpandrew.learnjava.util.ObjectMappers;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.bridge.SLF4JBridgeHandler;

public final class Handler extends GatewayRequestObjectHandler {
  // https://github.com/bbilger/jrestless#aws-usage-example
  public Handler() {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
    ResourceConfig resourceConfig = new ResourceConfig()
        .registerInstances(GraphResource.create())
        .registerInstances(SearchResource.create())
        .registerInstances(createJsonProvider())
        .register(GatewayFeature.class);
    init(resourceConfig);
    start();
  }

  private JacksonJaxbJsonProvider createJsonProvider() {
    JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
    provider.setMapper(ObjectMappers.create());
    return provider;
  }
}
