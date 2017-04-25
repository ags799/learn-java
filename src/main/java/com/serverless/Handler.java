package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

  private static final Logger LOG = Logger.getLogger(Handler.class);

  @Override
  public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
    LOG.info("received: " + input);
    Response response = ImmutableResponse.builder().message("some message").build();
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return ImmutableApiGatewayResponse.builder()
          .statusCode(200)
          .headers(ImmutableMap.of("X-Powered-By", "AWS Lambda & serverless"))
          .body(objectMapper.writeValueAsString(response))
          .isBase64Encoded(false)
          .build();
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
