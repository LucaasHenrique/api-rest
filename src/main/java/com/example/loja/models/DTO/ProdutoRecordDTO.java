package com.example.loja.models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoRecordDTO(@NotBlank String nome,
                               @NotNull BigDecimal valor) {
}
