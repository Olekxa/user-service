package com.greedobank.reports.dto;

import javax.validation.constraints.NotBlank;

public record ContentRequestDTO(
        @NotBlank(message = "Title can't be empty or null")
        String title,

        @NotBlank(message = "Description can't be empty or null")
        String description) {
}
