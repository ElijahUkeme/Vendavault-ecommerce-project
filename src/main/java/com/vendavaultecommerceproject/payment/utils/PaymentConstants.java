package com.vendavaultecommerceproject.payment.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;

public class PaymentConstants {

    @Value("${payStackSecretKey}")
    private static String payStackSecretKey;


}
