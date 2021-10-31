package com.roadlovers.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Manufacturer {
    FERRARI("Ferrari"),
    LAMBORGHINI("Lamborghini"),
    TOYOTA("Toyota"),
    HONDA("Honda"),
    HYUNDAI("Hyundai"),
    CHEVROLET("Chevrolet"),
    GEMBALLA("Gemballa"),
    NISSAN("Nissan"),
    MERCEDES_BENZ("Mercedes-Benz"),
    DODGE("Dodge"),
    HUMMER("Hummer"),
    MITSUBISHI("Mitsubishi");

    private final String description;
}
