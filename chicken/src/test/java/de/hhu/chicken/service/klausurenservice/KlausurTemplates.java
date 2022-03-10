package de.hhu.chicken.service.klausurenservice;

import de.hhu.chicken.domain.klausur.Klausur;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class KlausurTemplates {

  static List<Klausur> zweiBeispielklausuren() {
    Klausur klausur = new Klausur("Programmierung",
        LocalDate.of(2022, 3, 17),
        LocalTime.of(8, 30),
        LocalTime.of(9, 30),
        false,
        1234L);
    Klausur klausur2 = new Klausur("Programmierung",
        LocalDate.of(2022, 3, 19),
        LocalTime.of(8, 30),
        LocalTime.of(10, 30),
        true,
        1234L);
    return List.of(klausur, klausur2);
  }

  static Klausur beispielklausur() {
    return
        new Klausur("Programmierung",
            LocalDate.of(2022, 3, 17),
            LocalTime.of(8, 30),
            LocalTime.of(9, 30),
            false,
            1234L);
  }
}
