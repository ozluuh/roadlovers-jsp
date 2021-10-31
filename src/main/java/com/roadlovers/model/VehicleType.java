package com.roadlovers.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VehicleType {
    SUV("SUV"),
    UTILITY("Utilitário"),
    SPORTS("Esporte"),
    MUSCLE("Muscle"),
    LUXURY("Luxo"),
    EXOTIC("Exótico"),
    TUNER("Tuner");

    private final String description;
}
