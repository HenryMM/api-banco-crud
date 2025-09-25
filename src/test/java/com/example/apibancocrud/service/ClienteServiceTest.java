package com.example.apibancocrud.service;

import com.example.apibancocrud.model.Cliente;
import com.example.apibancocrud.model.ClienteDTO;
import com.example.apibancocrud.model.Persona;
import com.example.apibancocrud.repository.ClienteRepository;
import com.example.apibancocrud.repository.PersonaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private PersonaRepository personaRepository;
    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("findAll retorna lista de ClienteDTO")
    void findAll() {
        Cliente cliente = new Cliente();
        Persona persona = new Persona();
        persona.setNombre("Juan");
        persona.setIdentificacion("123");
        persona.setDireccion("Calle 1");
        cliente.setPersona(persona);
        cliente.setEstado("ACTIVO");
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente));
        List<ClienteDTO> result = clienteService.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNombre()).isEqualTo("Juan");
    }

    @Test
    @DisplayName("findById retorna ClienteDTO si existe")
    void findById_found() {
        Cliente cliente = new Cliente();
        Persona persona = new Persona();
        persona.setNombre("Ana");
        persona.setIdentificacion("456");
        persona.setDireccion("Calle 2");
        cliente.setPersona(persona);
        cliente.setEstado("ACTIVO");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        Optional<ClienteDTO> result = clienteService.findById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getNombre()).isEqualTo("Ana");
    }

    @Test
    @DisplayName("findById retorna vacío si no existe")
    void findById_notFound() {
        when(clienteRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<ClienteDTO> result = clienteService.findById(2L);
        assertThat(result).isNotPresent();
    }

    @Test
    @DisplayName("save guarda cliente y persona si es necesario")
    void save() {
        Cliente cliente = new Cliente();
        Persona persona = new Persona();
        cliente.setPersona(persona);
        when(personaRepository.save(any(Persona.class))).thenReturn(persona);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        Cliente result = clienteService.save(cliente);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("update actualiza cliente existente")
    void update_found() {
        Cliente cliente = new Cliente();
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(new Cliente()));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        Optional<Cliente> result = clienteService.update(1L, cliente);
        assertThat(result).isPresent();
    }

    @Test
    @DisplayName("update retorna vacío si no existe")
    void update_notFound() {
        Cliente cliente = new Cliente();
        when(clienteRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Cliente> result = clienteService.update(2L, cliente);
        assertThat(result).isNotPresent();
    }

    @Test
    @DisplayName("delete cambia estado a ELIMINADO si existe")
    void delete_found() {
        Cliente cliente = new Cliente();
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        boolean result = clienteService.delete(1L);
        assertThat(result).isTrue();
        assertThat(cliente.getEstado()).isEqualTo("ELIMINADO");
    }

    @Test
    @DisplayName("delete retorna false si no existe")
    void delete_notFound() {
        when(clienteRepository.findById(2L)).thenReturn(Optional.empty());
        boolean result = clienteService.delete(2L);
        assertThat(result).isFalse();
    }
}
