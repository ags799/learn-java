package com.serverless;

import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.jersey.JerseyLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.log4j.Logger;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class Handler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {
  private static final Logger LOG = Logger.getLogger(Handler.class);

  private final ResourceConfig jerseyApplication = new ResourceConfig()
      .packages("com.serverless")
      .register(JacksonFeature.class);
  private final JerseyLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler
      = JerseyLambdaContainerHandler.getAwsProxyHandler(jerseyApplication);

  @Override
  public AwsProxyResponse handleRequest(AwsProxyRequest awsProxyRequest, Context context) {
    LOG.info(jerseyApplication.getClasses());
    return handler.proxy(awsProxyRequest, context);
  }
}
