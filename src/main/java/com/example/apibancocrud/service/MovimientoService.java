package com.example.apibancocrud.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apibancocrud.dto.MovimientoDTO;
import com.example.apibancocrud.model.Movimiento;
import com.example.apibancocrud.repository.MovimientoRepository;

@Service
public class MovimientoService {
    private final MovimientoRepository movimientoRepository;

    @Autowired
    public MovimientoService(MovimientoRepository movimientoRepository) {
        this.movimientoRepository = movimientoRepository;
    }

    public List<MovimientoDTO> findAll() {
        return movimientoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<MovimientoDTO> findById(Long id) {
        return movimientoRepository.findById(id)
                .map(this::toDTO);
    }

    public Movimiento save(Movimiento movimiento) {
        return movimientoRepository.save(movimiento);
    }

    public Optional<Movimiento> update(Long id, Movimiento movimiento) {
        return movimientoRepository.findById(id)
                .map(existing -> {
                    movimiento.setId(id);
                    return movimientoRepository.save(movimiento);
                });
    }

    public boolean delete(Long id) {
        return movimientoRepository.findById(id)
                .map(movimiento -> {
                    movimientoRepository.delete(movimiento);
                    return true;
                }).orElse(false);
    }

    public MovimientoDTO toDTO(Movimiento movimiento) {
        String nombreCliente = null;
        String numeroCuenta = null;
        String tipoCuenta = null;
        if (movimiento.getCuenta() != null) {
            numeroCuenta = movimiento.getCuenta().getNumeroCuenta();
            tipoCuenta = movimiento.getCuenta().getTipo();
            if (movimiento.getCuenta().getCliente() != null && movimiento.getCuenta().getCliente().getPersona() != null) {
                nombreCliente = movimiento.getCuenta().getCliente().getPersona().getNombre();
            }
        }
        return new MovimientoDTO(
                movimiento.getFecha(),
                nombreCliente,
                numeroCuenta,
                tipoCuenta,
                movimiento.getSaldoInicial(),
                movimiento.getEstado(),
                movimiento.getMovimiento(),
                movimiento.getSaldoDisponible()
        );
    }
}
