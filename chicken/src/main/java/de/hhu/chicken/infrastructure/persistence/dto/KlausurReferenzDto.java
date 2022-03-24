package de.hhu.chicken.infrastructure.persistence.dto;

import org.springframework.data.relational.core.mapping.Table;

@Table("klausurreferenz")
public record KlausurReferenzDto(Long klausurId) {
}
