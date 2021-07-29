package br.com.techstack.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.techstack.model.Incident;
import br.com.techstack.repository.IncidentRepository;

@Service
public class IncidentService {

	@Autowired
	private IncidentRepository repository;

	public List<Incident> getAllIncidents() {
		return repository.findAll();
	}

	public List<Incident> getIncidentsByName(String name) {
		return repository.findByNameContainingIgnoreCase(name);
	}

	public Optional<Incident> getIncidentById(Long id) {
		return repository.findById(id);
	}

	public Incident postIncident(Incident incident) {
		return repository.save(incident);
	}

	public void deleteIncident(Long id) {
		repository.deleteById(id);
	}
}
