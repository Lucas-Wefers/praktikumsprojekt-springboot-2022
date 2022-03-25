package de.hhu.chicken.infrastructure.persistence.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("klausur")
public record KlausurDto(@Id Long id,
                         String fach,
                         LocalDate datum,
                         LocalTime von,
                         LocalTime bis,
                         boolean isPraesenz,
                         Long veranstaltungsId) {

}
