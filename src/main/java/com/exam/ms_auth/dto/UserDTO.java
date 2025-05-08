package com.exam.ms_auth.controller;

import com.exam.ms_auth.entity.Rol;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String nombre;
    private String email;
    private Rol rol;
}