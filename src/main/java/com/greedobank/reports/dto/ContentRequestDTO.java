package com.greedobank.reports.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ContentRequestDTO(
        @NotNull(message = "title  can't be null")
        @NotBlank(message = "title¬ can't be empty")
        String title,

        @NotNull(message = "description can't be null")
        @NotBlank(message = "description can't be empty")
        String description) {
}
