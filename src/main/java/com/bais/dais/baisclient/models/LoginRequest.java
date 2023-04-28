package com.bais.dais.baisclient.models;

import lombok.*;

@AllArgsConstructor
public class LoginRequest {
    @Getter
    @Setter
    private String login;

    @Getter
    @Setter
    private String password;
}

