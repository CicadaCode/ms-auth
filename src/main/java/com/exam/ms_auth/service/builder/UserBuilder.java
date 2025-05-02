package com.exam.ms_auth.service.builder;

import com.exam.ms_auth.entity.Rol;
import com.exam.ms_auth.entity.User;

public class UserBuilder {
    private Long id;
    private String nombre;
    private String email;
    private String password;
    private Rol rol;

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public UserBuilder id(Long id) { this.id = id; return this; }
    public UserBuilder nombre(String n) { this.nombre = n; return this; }
    public UserBuilder email(String e) { this.email = e; return this; }
    public UserBuilder password(String p) { this.password = p; return this; }
    public UserBuilder rol(Rol r) { this.rol = r; return this; }

    public User build() {
        User u = new User();
        u.setId(id);
        u.setNombre(nombre);
        u.setEmail(email);
        u.setPassword(password);
        u.setRol(rol);
        return u;
    }
}
