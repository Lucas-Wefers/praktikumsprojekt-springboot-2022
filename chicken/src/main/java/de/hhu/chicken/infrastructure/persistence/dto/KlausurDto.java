package de.hhu.chicken.infrastructure.persistence.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import org.springframework.data.annotation.Id;

public record KlausurDto(@Id Long id,
                         String fach,
                         LocalDate datum,
                         LocalTime von,
                         LocalTime bis,
                         boolean isPraesenz,
                         Long veranstalungsId) {
}
