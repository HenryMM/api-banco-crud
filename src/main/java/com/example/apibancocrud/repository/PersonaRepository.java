package com.example.apibancocrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apibancocrud.model.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
}
