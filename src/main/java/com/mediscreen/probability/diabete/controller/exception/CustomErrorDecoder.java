package com.mediscreen.probability.diabete.controller.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String invoque, Response response) {
        if (response.status() == 404) {
       log.error("Error - An element is missing to build the report ");
            return new BadRequestException(
                    "Not Found"
            );
        }

        return defaultErrorDecoder.decode(invoque, response);
    }
}
