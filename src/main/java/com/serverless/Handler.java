package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Collections;
import org.apache.log4j.Logger;

public class Handler implements RequestHandler<Request, ApiGatewayResponse> {

  private static final Logger LOG = Logger.getLogger(Handler.class);

  @Override
  public ApiGatewayResponse handleRequest(Request request, Context context) {
    LOG.info("received: " + request);
    Response responseBody = ImmutableResponse.builder()
        .message("Go Serverless v1.x! Your function executed successfully!")
        .request(request)
        .build();
    return ApiGatewayResponse.builder()
        .setStatusCode(200)
        .setObjectBody(responseBody)
        .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
        .build();
  }
}
