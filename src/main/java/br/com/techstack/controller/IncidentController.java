package br.com.techstack.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.techstack.model.Incident;
import br.com.techstack.model.enums.IncidentSituation;
import br.com.techstack.service.IncidentService;

@RestController
@RequestMapping(path = "/incidents")
public class IncidentController {

	@Autowired
	private IncidentService service;

	@GetMapping(path = { "/", "" }, produces = "application/json;charset=UTF-8")
	public List<Incident> listIncidents(@RequestParam Map<String, String> parameters) {
		if (parameters.isEmpty()) {
			return service.getAllIncidents();
		}
		return service.getIncidentsByName(parameters.get("name"));
	}

	@GetMapping(path = { "/{id}" }, produces = "application/json;charset=UTF-8")
	public Incident findOneIncident(@PathVariable(value = "id") Long id) {
		return service.getIncidentById(id).orElseThrow(() -> new EntityNotFoundException("Incident not found."));
	}

	@PostMapping(path = { "/", "" }, produces = "application/json;charset=UTF-8")
	public Incident saveIncident(@RequestBody Incident incident) {
		incident.setSituation(IncidentSituation.OPEN);
		incident.setCreatedAt(LocalDate.now());
		incident.setUpdatedAt(null);
		incident.setClosedAt(null);
		return service.postIncident(incident);
	}

	@PutMapping(value = "/start/{id}", produces = "application/json;charset=UTF-8")
	public ResponseEntity<Incident> startIncident(@PathVariable(value = "id") Long id, @RequestBody Incident incident) {
		if (incident.getClosedAt() == null) {
			return service.getIncidentById(id).map(incidentData -> {
				incidentData.setName(incident.getName());
				incidentData.setDescription(incident.getDescription());
				incidentData.setSituation(IncidentSituation.IN_PROGRESS);
				incidentData.setCreatedAt(incidentData.getCreatedAt());
				incidentData.setUpdatedAt(LocalDate.now());
				incidentData.setClosedAt(null);
				Incident incidentUpdt = service.postIncident(incidentData);
				return ResponseEntity.ok().body(incidentUpdt);
			}).orElse(ResponseEntity.notFound().build());
		}
		return ResponseEntity.badRequest().body(incident);
	}

	@PutMapping(value = "/close/{id}", produces = "application/json;charset=UTF-8")
	public ResponseEntity<Incident> closeIncident(@PathVariable(value = "id") Long id, @RequestBody Incident incident) {
		if (incident.getClosedAt() == null) {
			return service.getIncidentById(id).map(incidentData -> {
				incidentData.setName(incident.getName());
				incidentData.setDescription(incident.getDescription());
				incidentData.setSituation(IncidentSituation.CLOSED);
				incidentData.setCreatedAt(incidentData.getCreatedAt());
				incidentData.setUpdatedAt(LocalDate.now());
				incidentData.setClosedAt(LocalDate.now());
				Incident incidentUpdt = service.postIncident(incidentData);
				return ResponseEntity.ok().body(incidentUpdt);
			}).orElse(ResponseEntity.notFound().build());
		}
		return ResponseEntity.badRequest().body(incident);
	}

	@DeleteMapping(path = { "/{id}" })
	public void deleteIncident(@PathVariable(value = "id") Long id) {
		service.deleteIncident(id);
	}
}
