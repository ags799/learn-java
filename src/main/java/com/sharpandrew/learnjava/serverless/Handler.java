package com.sharpandrew.learnjava.serverless;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.jrestless.aws.gateway.GatewayFeature;
import com.jrestless.aws.gateway.handler.GatewayRequestObjectHandler;
import com.sharpandrew.learnjava.graph.GraphResource;
import com.sharpandrew.learnjava.graph.PathResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.bridge.SLF4JBridgeHandler;

public final class Handler extends GatewayRequestObjectHandler {
  // https://github.com/bbilger/jrestless#aws-usage-example
  public Handler() {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
    ResourceConfig resourceConfig = new ResourceConfig()
        .registerInstances(GraphResource.create())
        .registerInstances(PathResource.create())
        .registerInstances(createJsonProvider())
        .register(GatewayFeature.class);
    init(resourceConfig);
    start();
  }

  private JacksonJaxbJsonProvider createJsonProvider() {
    JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
    ObjectMapper mapper = new ObjectMapper().registerModule(new Jdk8Module());
    provider.setMapper(mapper);
    return provider;
  }
}
