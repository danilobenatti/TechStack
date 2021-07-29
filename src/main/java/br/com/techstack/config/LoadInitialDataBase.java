package br.com.techstack.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.techstack.model.Incident;
import br.com.techstack.model.enums.IncidentSituation;
import br.com.techstack.service.IncidentService;

@Configuration
@Profile(value = "dev")
public class LoadInitialDataBase {

	@Bean
	CommandLineRunner runner(IncidentService incidentService) {
		return args -> {
			var incident1 = new Incident(null, "nome do incidente", "descricao do incidente", IncidentSituation.OPEN, LocalDate.now(), null, null);
			incidentService.postIncident(incident1);
			
		};
	}
}
