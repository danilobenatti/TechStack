package br.com.techstack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.techstack.model.Incident;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {

	List<Incident> findByNameContainingIgnoreCase(String name);
}
