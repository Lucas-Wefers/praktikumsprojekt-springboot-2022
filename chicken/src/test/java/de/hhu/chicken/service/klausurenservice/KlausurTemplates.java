package de.hhu.chicken.service.klausurenservice;

import static org.mockito.Mockito.mock;

import de.hhu.chicken.domain.klausur.Klausurart;
import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.domain.klausur.VeranstaltungsId;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class KlausurTemplates {

  static List<Klausur> zweiBeispielklausuren() {
    Klausur klausur = new Klausur("Programmierung",
        LocalDate.of(2022, 3, 17),
        LocalTime.of(8, 30),
        LocalTime.of(9, 30),
        Klausurart.ONLINE,
        mock(VeranstaltungsId.class));
    Klausur klausur2 = new Klausur("Programmierung",
        LocalDate.of(2022, 3, 19),
        LocalTime.of(8, 30),
        LocalTime.of(10, 30),
        Klausurart.PRAESENZ,
        mock(VeranstaltungsId.class));
    return List.of(klausur, klausur2);
  }

  static Klausur beispielklausur() {
    return
        new Klausur("Programmierung",
            LocalDate.of(2022, 3, 17),
            LocalTime.of(8, 30),
            LocalTime.of(9, 30),
            Klausurart.ONLINE,
            mock(VeranstaltungsId.class));
  }
}
