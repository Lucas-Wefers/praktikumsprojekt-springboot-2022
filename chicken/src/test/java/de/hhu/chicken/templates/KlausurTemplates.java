package de.hhu.chicken.templates;

import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.infrastructure.persistence.dto.KlausurDto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class KlausurTemplates {

  public static List<Klausur> zweiBeispielklausuren() {
    Klausur klausur = new Klausur(1L,
        "Programmierung",
        LocalDate.of(2022, 3, 17),
        LocalTime.of(8, 30),
        LocalTime.of(9, 30),
        false,
        1234L);
    Klausur klausur2 = new Klausur(2L,
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
        new Klausur(1L,
            "Programmierung",
            LocalDate.of(2022, 3, 17),
            LocalTime.of(8, 30),
            LocalTime.of(9, 30),
            false,
            1234L);
  }

  public static Klausur beispielklausur2() {
    return
        new Klausur(1L,
            "Programmierung",
            LocalDate.of(2022, 3, 17),
            LocalTime.of(10, 30),
            LocalTime.of(11, 30),
            false,
            1234L);
  }

  public static Klausur beispielklausur3() {
    return
        new Klausur(2L,
            "Programmierung",
            LocalDate.of(2022, 3, 17),
            LocalTime.of(12, 15),
            LocalTime.of(13, 0),
            false,
            1234L);
  }

  public static KlausurDto beispielklausurDto() {
    return
        new KlausurDto(1L,
            "Programmierung",
            LocalDate.of(2022, 3, 17),
            LocalTime.of(8, 30),
            LocalTime.of(9, 30),
            false,
            1234L);
  }

  public static List<KlausurDto> zweiBeispielklausurenDtos() {
    KlausurDto klausur = new KlausurDto(1L,
        "Programmierung",
        LocalDate.of(2022, 3, 17),
        LocalTime.of(8, 30),
        LocalTime.of(9, 30),
        false,
        1234L);
    KlausurDto klausur2 = new KlausurDto(2L,
        "Programmierung",
        LocalDate.of(2022, 3, 19),
        LocalTime.of(8, 30),
        LocalTime.of(10, 30),
        true,
        1234L);
    return List.of(klausur, klausur2);
  }
}
