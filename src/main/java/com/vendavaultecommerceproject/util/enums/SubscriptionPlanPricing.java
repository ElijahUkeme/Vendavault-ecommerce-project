package com.vendavaultecommerceproject.util.enums;


import lombok.Getter;

@Getter
public enum SubscriptionPlanPricing {

    BASIC("Basic"),
    STANDARD("Standard"),
    PREMIUM("Premium");

    private final String value;
    SubscriptionPlanPricing(String value) {
        this.value = value;
    }
}
