package com.example.apibancocrud.repository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.apibancocrud.model.Cliente;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("Guardar y buscar cliente por ID")
    void saveAndFindById() {
        Cliente cliente = new Cliente();
        cliente.setEstado("ACTIVO");
        // Puedes setear más campos obligatorios aquí
        Cliente saved = clienteRepository.save(cliente);
        Optional<Cliente> found = clienteRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getEstado()).isEqualTo("ACTIVO");
    }

    @Test
    @DisplayName("Guardar cliente usando saveWithLog")
    void saveWithLog() {
        Cliente cliente = new Cliente();
        cliente.setEstado("PRUEBA_LOG");
        Cliente saved = clienteRepository.saveWithLog(cliente);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getEstado()).isEqualTo("PRUEBA_LOG");
    }

    @Test
    @DisplayName("Eliminar cliente")
    void deleteCliente() {
        Cliente cliente = new Cliente();
        cliente.setEstado("ELIMINAR");
        Cliente saved = clienteRepository.save(cliente);
        clienteRepository.delete(saved);
        Optional<Cliente> found = clienteRepository.findById(saved.getId());
        assertThat(found).isNotPresent();
    }
}
