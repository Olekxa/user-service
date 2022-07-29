package com.greedobank.reports.dto;

import javax.validation.constraints.NotBlank;

public record ContentRequestDTO(
        @NotBlank
        String title,
        @NotBlank
        String description) {
}
