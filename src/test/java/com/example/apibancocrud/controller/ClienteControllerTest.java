package com.example.apibancocrud.controller;

import com.example.apibancocrud.model.Cliente;
import com.example.apibancocrud.model.ClienteDTO;
import com.example.apibancocrud.service.ClienteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    public ClienteControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Obtener todos los clientes")
    void getAllClientes() {
        ClienteDTO dto = new ClienteDTO("Juan", "123", "Calle 1", "ACTIVO");
        when(clienteService.findAll()).thenReturn(Arrays.asList(dto));
        ResponseEntity<List<ClienteDTO>> response = clienteController.getAllClientes();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    @DisplayName("Obtener cliente por id existente")
    void getClienteById_found() {
        ClienteDTO dto = new ClienteDTO("Ana", "456", "Calle 2", "ACTIVO");
        when(clienteService.findById(1L)).thenReturn(Optional.of(dto));
        ResponseEntity<ClienteDTO> response = clienteController.getClienteById(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(dto);
    }

    @Test
    @DisplayName("Obtener cliente por id no existente")
    void getClienteById_notFound() {
        when(clienteService.findById(2L)).thenReturn(Optional.empty());
        ResponseEntity<ClienteDTO> response = clienteController.getClienteById(2L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Crear cliente")
    void createCliente() {
        Cliente cliente = new Cliente();
        Cliente saved = new Cliente();
        when(clienteService.save(any(Cliente.class))).thenReturn(saved);
        ResponseEntity<Cliente> response = clienteController.createCliente(cliente);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(saved);
    }

    @Test
    @DisplayName("Actualizar cliente existente")
    void updateCliente_found() {
        Cliente cliente = new Cliente();
        when(clienteService.update(eq(1L), any(Cliente.class))).thenReturn(Optional.of(cliente));
        ResponseEntity<Cliente> response = clienteController.updateCliente(1L, cliente);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(cliente);
    }

    @Test
    @DisplayName("Actualizar cliente no existente")
    void updateCliente_notFound() {
        Cliente cliente = new Cliente();
        when(clienteService.update(eq(2L), any(Cliente.class))).thenReturn(Optional.empty());
        ResponseEntity<Cliente> response = clienteController.updateCliente(2L, cliente);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Eliminar cliente existente")
    void deleteCliente_found() {
        when(clienteService.delete(1L)).thenReturn(true);
        ResponseEntity<Void> response = clienteController.deleteCliente(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Eliminar cliente no existente")
    void deleteCliente_notFound() {
        when(clienteService.delete(2L)).thenReturn(false);
        ResponseEntity<Void> response = clienteController.deleteCliente(2L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
