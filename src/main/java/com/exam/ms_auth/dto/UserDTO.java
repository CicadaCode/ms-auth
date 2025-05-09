package com.exam.ms_auth.dto;

import com.exam.ms_auth.entity.Rol;
import com.exam.ms_auth.entity.User;
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

    public UserDTO(User user) {
        this.id = user.getId();
        this.nombre = user.getNombre();
        this.email = user.getEmail();
        this.rol = user.getRol();
    }
}