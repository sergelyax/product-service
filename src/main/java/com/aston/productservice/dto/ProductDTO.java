package com.aston.productservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;

    @JsonProperty("name")
    @NotNull
    @Size(min = 1, max = 255)
    private String productName;

    @JsonProperty("type")
    @NotNull
    @Size(min = 1, max = 255)
    private String productType;

    @JsonProperty("price")
    @NotNull
    @Positive
    private Double productPrice;

}
