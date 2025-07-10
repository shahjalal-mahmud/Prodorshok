package com.example.prodorshok.domain.model

data class CareerStep(
    val text: String,
    val subSteps: List<CareerStep> = emptyList()
)
