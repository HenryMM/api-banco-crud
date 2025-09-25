
package com.example.apibancocrud.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.apibancocrud.model.Cliente;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apibancocrud.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Logger logger = LoggerFactory.getLogger(ClienteRepository.class);

	// Ejemplo de método personalizado con log
	default Cliente saveWithLog(Cliente cliente) {
		logger.info("Guardando cliente en repository: {}", cliente);
		return save(cliente);
	}

	// Puedes agregar más métodos personalizados con logs si lo requieres
}
