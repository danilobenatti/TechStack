package br.com.techstack.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.techstack.model.enums.IncidentSituation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Accessors(chain = true)
@Builder
@Entity
@Table(name = "incidents")
public class Incident {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	@Column(name = "idIncident")
	private Long id;

	@Column(length = 150)
	private String name;

	@Column(length = 500)
	private String description;
	
	private IncidentSituation situation;

	private LocalDate createdAt;

	private LocalDate updatedAt;

	private LocalDate closedAt;
}
