package com.example.apibancocrud.repository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.apibancocrud.model.Cuenta;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CuentaRepositoryTest {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Test
    @DisplayName("Guardar y buscar cuenta por ID")
    void saveAndFindById() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("1234567890");
        cuenta.setTipo("AHORRO");
        cuenta.setSaldoInicial(BigDecimal.valueOf(1000));
        cuenta.setEstado("ACTIVA");
        Cuenta saved = cuentaRepository.save(cuenta);
        Optional<Cuenta> found = cuentaRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getNumeroCuenta()).isEqualTo("1234567890");
    }

    @Test
    @DisplayName("Eliminar cuenta")
    void deleteCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("9876543210");
        cuenta.setTipo("CORRIENTE");
        cuenta.setSaldoInicial(BigDecimal.valueOf(500));
        cuenta.setEstado("ACTIVA");
        Cuenta saved = cuentaRepository.save(cuenta);
        cuentaRepository.delete(saved);
        Optional<Cuenta> found = cuentaRepository.findById(saved.getId());
        assertThat(found).isNotPresent();
    }
}
