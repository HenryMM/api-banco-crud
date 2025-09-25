package com.example.apibancocrud.service;

import com.example.apibancocrud.model.Cuenta;
import com.example.apibancocrud.repository.CuentaRepository;
import com.example.apibancocrud.dto.CuentaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CuentaService {
    private final CuentaRepository cuentaRepository;

    @Autowired
    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }


    public List<CuentaDTO> findAll() {
        return cuentaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CuentaDTO> findById(Long id) {
        return cuentaRepository.findById(id)
                .map(this::toDTO);
    }

    public Cuenta save(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public Optional<Cuenta> update(Long id, Cuenta cuenta) {
        return cuentaRepository.findById(id)
                .map(existing -> {
                    cuenta.setId(id);
                    return cuentaRepository.save(cuenta);
                });
    }

    public boolean delete(Long id) {
        return cuentaRepository.findById(id)
                .map(cuenta -> {
                    cuentaRepository.delete(cuenta);
                    return true;
                }).orElse(false);
    }
    
    public CuentaDTO toDTO(Cuenta cuenta) {
        String nombreCliente = cuenta.getCliente() != null && cuenta.getCliente().getPersona() != null
                ? cuenta.getCliente().getPersona().getNombre() : null;
        return new CuentaDTO(
                cuenta.getNumeroCuenta(),
                cuenta.getTipo(),
                cuenta.getSaldoInicial(),
                cuenta.getEstado(),
                nombreCliente
        );
    }
}
