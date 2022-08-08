package com.greedobank.reports.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ContentRequestDTO(
        @NotNull
        @NotBlank
        String title,

        @NotNull
        @NotBlank
        String description) {
}
