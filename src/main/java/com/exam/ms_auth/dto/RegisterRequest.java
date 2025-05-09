package com.exam.ms_auth.dto;

import com.exam.ms_auth.entity.Rol;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String nombre;
    private String email;
    private String password;
    private Rol rol;
}