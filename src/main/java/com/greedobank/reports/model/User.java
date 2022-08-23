package com.greedobank.reports.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
        private int id;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private Role role;
}
