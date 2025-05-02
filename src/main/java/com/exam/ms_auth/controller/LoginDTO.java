package com.exam.ms_auth.controller;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String password;
}