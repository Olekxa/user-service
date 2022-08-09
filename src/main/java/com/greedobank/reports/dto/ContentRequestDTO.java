package com.greedobank.reports.dto;

import javax.validation.constraints.NotBlank;

public record ContentRequestDTO(
        @NotBlank(message = "title can't be empty or null")
        String title,

        @NotBlank(message = "description can't be empty or null")
        String description) {
}
