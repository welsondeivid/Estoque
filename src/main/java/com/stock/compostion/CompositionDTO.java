package com.stock.compostion;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CompositionDTO {

    @NotNull
    public String rawMaterialCode;

    @Positive
    public int required;
}