package com.vendavaultecommerceproject.payment.service.template;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
        @Override
        public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
            HttpStatus statusCode = (HttpStatus) httpResponse.getStatusCode();
            return (statusCode.is4xxClientError() || statusCode.is5xxServerError());
        }


        @Override
        public void handleError(ClientHttpResponse httpResponse) throws IOException {
            HttpStatus statusCode = (HttpStatus) httpResponse.getStatusCode();

            if (statusCode.is5xxServerError()) {
                // handle SERVER_ERROR
            } else if (statusCode.is4xxClientError()) {
                // handle CLIENT_ERROR
                if (statusCode == HttpStatus.NOT_FOUND) {
                    try {
                        throw new ChangeSetPersister.NotFoundException();
                    } catch (ChangeSetPersister.NotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

    }

