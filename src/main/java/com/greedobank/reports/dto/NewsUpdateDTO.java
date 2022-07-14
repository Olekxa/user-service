package com.greedobank.reports.dto;

public record NewsUpdateDTO(
        boolean displayOnSite,
        boolean sendByEmail,
        ContentUpdateDTO content) {
}
