package de.hhu.chicken.templates;

import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.infrastructure.persistence.dto.KlausurDto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class KlausurTemplates {

  public static List<Klausur> zweiBeispielklausuren() {
    Klausur klausur = new Klausur(UUID.fromString("d068927a-f5ed-49f4-97b3-f05b36afed5a"),
        "Programmierung",
        LocalDate.of(2022, 3, 17),
        LocalTime.of(8, 30),
        LocalTime.of(9, 30),
        false,
        1234L);
    Klausur klausur2 = new Klausur(UUID.fromString("01aebee7-a544-43ab-8768-1231a2f6a4f3"),
        "Programmierung",
        LocalDate.of(2022, 3, 19),
        LocalTime.of(8, 30),
        LocalTime.of(10, 30),
        true,
        1234L);
    return List.of(klausur, klausur2);
  }

  public static Klausur beispielklausur() {
    return
        new Klausur("Programmierung",
            LocalDate.of(2022, 3, 17),
            LocalTime.of(8, 30),
            LocalTime.of(9, 30),
            false,
            1234L);
  }

  public static List<KlausurDto> zweiBeispielklausurenDtos() {
    KlausurDto klausur = new KlausurDto(UUID.fromString("d068927a-f5ed-49f4-97b3-f05b36afed5a"),
        "Programmierung",
        LocalDate.of(2022, 3, 17),
        LocalTime.of(8, 30),
        LocalTime.of(9, 30),
        false,
        1234L);
    KlausurDto klausur2 = new KlausurDto(UUID.fromString("01aebee7-a544-43ab-8768-1231a2f6a4f3"),
        "Programmierung",
        LocalDate.of(2022, 3, 19),
        LocalTime.of(8, 30),
        LocalTime.of(10, 30),
        true,
        1234L);
    return List.of(klausur, klausur2);
  }
}
