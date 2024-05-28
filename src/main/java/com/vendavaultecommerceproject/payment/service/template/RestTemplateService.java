package com.vendavaultecommerceproject.payment.service.template;

import com.vendavaultecommerceproject.payment.response.common.CustomPaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
    public class RestTemplateService {
        private RestTemplate restTemplate;

        @Autowired
        public RestTemplateService(RestTemplateBuilder restTemplateBuilder) {
            this.restTemplate = restTemplateBuilder
                    .errorHandler(new RestTemplateResponseErrorHandler())
                    .build();
        }

        public <T> ResponseEntity<CustomPaymentResponse> post(String url, T req, HttpHeaders headers) {

            HttpEntity entity = headers == null ? new HttpEntity(req) : new HttpEntity(req, headers);
            ResponseEntity<CustomPaymentResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, CustomPaymentResponse.class);
            return response;
        }

        public ResponseEntity<CustomPaymentResponse> get(String url, HttpHeaders headers) {
            HttpEntity entity = headers == null ? null : new HttpEntity(headers);
            ResponseEntity<CustomPaymentResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, CustomPaymentResponse.class);

            return response;
        }

        public ResponseEntity<CustomPaymentResponse> delete(String url, HttpHeaders headers) {
            HttpEntity entity = headers == null ? null : new HttpEntity(headers);
            ResponseEntity<CustomPaymentResponse> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, CustomPaymentResponse.class);

            return response;
        }
    }

