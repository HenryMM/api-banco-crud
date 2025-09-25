package com.example.apibancocrud.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apibancocrud.model.Cliente;
import com.example.apibancocrud.model.ClienteDTO;
import com.example.apibancocrud.repository.ClienteRepository;
import com.example.apibancocrud.repository.PersonaRepository;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final PersonaRepository personaRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, PersonaRepository personaRepository) {
        this.clienteRepository = clienteRepository;
        this.personaRepository = personaRepository;
    }

    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ClienteDTO> findById(Long id) {
        return clienteRepository.findById(id).map(this::toDTO);
    }

    private ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null || cliente.getPersona() == null) {
            return null;
        }
        return new ClienteDTO(
                cliente.getPersona().getNombre(),
                cliente.getPersona().getIdentificacion(),
                cliente.getPersona().getDireccion(),
                cliente.getEstado()
        );
    }

    public Cliente save(Cliente cliente) {
        // Guardar primero la persona asociada (si no tiene id)
        if (cliente.getPersona() != null && cliente.getPersona().getId() == null) {
            cliente.setPersona(personaRepository.save(cliente.getPersona()));
        }
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> update(Long id, Cliente cliente) {
        return clienteRepository.findById(id)
                .map(existing -> {
                    cliente.setId(id);
                    return clienteRepository.save(cliente);
                });
    }

    public boolean delete(Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    clienteRepository.delete(cliente);
                    return true;
                }).orElse(false);
    }
}
