package com.sharpandrew.learnjava;

import com.jrestless.aws.gateway.GatewayFeature;
import com.jrestless.aws.gateway.handler.GatewayRequestObjectHandler;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.bridge.SLF4JBridgeHandler;

public final class Handler extends GatewayRequestObjectHandler {
  /** copied from https://github.com/bbilger/jrestless#aws-usage-example */
  public Handler() {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
    ResourceConfig resourceConfig = new ResourceConfig()
        .registerInstances(GraphResource.create())
        .register(GatewayFeature.class)
        // these classes are for generating Swagger definitions
        .registerClasses(ApiListingResource.class, SwaggerSerializers.class);
    BeanConfig beanConfig = new BeanConfig();
    beanConfig.setBasePath("/api");
    beanConfig.setScan(true);
    init(resourceConfig);
    start();
  }
}
