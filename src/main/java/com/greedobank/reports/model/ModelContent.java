package com.greedobank.reports.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@AllArgsConstructor
public class ModelContent {
    @NotBlank
    @Column(name = "title", nullable = false)
    String title;

    @NotBlank
    @Column(name = "description", nullable = false)
    String description;
}
