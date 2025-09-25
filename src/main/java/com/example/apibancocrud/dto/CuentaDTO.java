package com.example.apibancocrud.dto;

public class CuentaDTO {
    private String numeroCuenta;
    private String tipo;
    private java.math.BigDecimal saldoInicial;
    private String estado;
    private String nombreCliente;

    public CuentaDTO() {}

    public CuentaDTO(String numeroCuenta, String tipo, java.math.BigDecimal saldoInicial, String estado, String nombreCliente) {
        this.numeroCuenta = numeroCuenta;
        this.tipo = tipo;
        this.saldoInicial = saldoInicial;
        this.estado = estado;
        this.nombreCliente = nombreCliente;
    }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public java.math.BigDecimal getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(java.math.BigDecimal saldoInicial) { this.saldoInicial = saldoInicial; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
}
