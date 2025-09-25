package com.example.apibancocrud.model;

public class ClienteDTO {
    private String nombre;
    private String identificacion;
    private String direccion;
    private String estado;

    public ClienteDTO() {}

    public ClienteDTO(String nombre, String identificacion, String direccion, String estado) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.direccion = direccion;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
