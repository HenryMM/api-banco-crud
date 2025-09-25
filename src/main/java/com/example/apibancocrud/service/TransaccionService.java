package com.example.apibancocrud.service;

import com.example.apibancocrud.model.Cuenta;
import com.example.apibancocrud.model.Movimiento;
import com.example.apibancocrud.repository.CuentaRepository;
import com.example.apibancocrud.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransaccionService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;


    public Movimiento retirar(String numeroCuenta, BigDecimal monto, String codigoTransaccion) {
        Optional<Cuenta> cuentaOpt = cuentaRepository.findAll().stream()
                .filter(c -> numeroCuenta.equals(c.getNumeroCuenta()))
                .findFirst();
        if (cuentaOpt.isEmpty()) {
            throw new IllegalArgumentException("Cuenta no encontrada");
        }
        Cuenta cuenta = cuentaOpt.get();
        // Obtener el último saldo disponible
        BigDecimal saldoAnterior = cuenta.getSaldoInicial();
        Optional<Movimiento> ultimoMov = movimientoRepository.findAll().stream()
                .filter(m -> m.getCuenta() != null && m.getCuenta().getId().equals(cuenta.getId()))
                .reduce((first, second) -> second);
        if (ultimoMov.isPresent()) {
            saldoAnterior = ultimoMov.get().getSaldoDisponible();
        }
        if (saldoAnterior.compareTo(monto) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuenta);
        movimiento.setMovimiento(monto.negate());
        movimiento.setSaldoInicial(saldoAnterior);
        movimiento.setSaldoDisponible(saldoAnterior.subtract(monto));
        movimiento.setEstado("ACTIVO");
        movimiento.setFecha(java.time.LocalDateTime.now());
        return movimientoRepository.save(movimiento);
    }


    @Autowired
    public TransaccionService(CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }

    public Movimiento depositar(String numeroCuenta, BigDecimal monto, String codigoTransaccion) {
        Optional<Cuenta> cuentaOpt = cuentaRepository.findAll().stream()
                .filter(c -> numeroCuenta.equals(c.getNumeroCuenta()))
                .findFirst();
        if (cuentaOpt.isEmpty()) {
            throw new IllegalArgumentException("Cuenta no encontrada");
        }
        Cuenta cuenta = cuentaOpt.get();
        // Obtener el último saldo disponible
        BigDecimal saldoAnterior = cuenta.getSaldoInicial();
        Optional<Movimiento> ultimoMov = movimientoRepository.findAll().stream()
                .filter(m -> m.getCuenta() != null && m.getCuenta().getId().equals(cuenta.getId()))
                .reduce((first, second) -> second);
        if (ultimoMov.isPresent()) {
            saldoAnterior = ultimoMov.get().getSaldoDisponible();
        }
        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuenta);
        movimiento.setMovimiento(monto);
        movimiento.setSaldoInicial(saldoAnterior);
        movimiento.setSaldoDisponible(saldoAnterior.add(monto));
        movimiento.setEstado("ACTIVO");
        movimiento.setFecha(java.time.LocalDateTime.now());
        return movimientoRepository.save(movimiento);
    }
}
