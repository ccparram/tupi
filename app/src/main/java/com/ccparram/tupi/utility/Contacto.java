package com.ccparram.tupi.utility;

/**
 * Created by camilow8 on 19/09/2015.
 */
public class Contacto {

    private String img;
    private String nombre;
    private String telefono;
    private String email;
    private String direccion;

    public Contacto(String img, String nombre, String telefono, String email, String direccion) {
        this.img = img;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getDireccion() {
        return direccion;
    }
}
