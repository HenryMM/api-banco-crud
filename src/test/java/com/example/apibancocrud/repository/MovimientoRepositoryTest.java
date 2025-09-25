package com.example.apibancocrud.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.apibancocrud.model.Movimiento;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class MovimientoRepositoryTest {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Test
    @DisplayName("Guardar y buscar movimiento por ID")
    void saveAndFindById() {
        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setSaldoInicial(BigDecimal.valueOf(1000));
        movimiento.setEstado("ACTIVO");
        movimiento.setMovimiento(BigDecimal.valueOf(200));
        movimiento.setSaldoDisponible(BigDecimal.valueOf(1200));
        Movimiento saved = movimientoRepository.save(movimiento);
        Optional<Movimiento> found = movimientoRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getEstado()).isEqualTo("ACTIVO");
    }

    @Test
    @DisplayName("Eliminar movimiento")
    void deleteMovimiento() {
        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setSaldoInicial(BigDecimal.valueOf(500));
        movimiento.setEstado("INACTIVO");
        movimiento.setMovimiento(BigDecimal.valueOf(100));
        movimiento.setSaldoDisponible(BigDecimal.valueOf(600));
        Movimiento saved = movimientoRepository.save(movimiento);
        movimientoRepository.delete(saved);
        Optional<Movimiento> found = movimientoRepository.findById(saved.getId());
        assertThat(found).isNotPresent();
    }
}
