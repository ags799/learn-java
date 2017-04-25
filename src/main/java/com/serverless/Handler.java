package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Collections;
import java.util.Map;
import org.apache.log4j.Logger;

public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

  private static final Logger LOG = Logger.getLogger(Handler.class);

  @Override
  public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
    LOG.info("received: " + input);
    return ImmutableApiGatewayResponse.builder()
        .statusCode(200)
        .headers(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
        .body(ImmutableResponse.builder().message("some message").build().toString())
        .build();
  }
}
