package com.greedobank.reports.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
        private int id;
        private String email;
        private Role role;
}
