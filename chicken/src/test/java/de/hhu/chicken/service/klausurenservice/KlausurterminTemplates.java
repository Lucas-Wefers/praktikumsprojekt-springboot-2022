package de.hhu.chicken.service.klausurenservice;

import static org.mockito.Mockito.mock;

import de.hhu.chicken.domain.klausur.Klausurart;
import de.hhu.chicken.domain.klausur.Klausurtermin;
import de.hhu.chicken.domain.klausur.VeranstaltungsId;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class KlausurterminTemplates {

  static List<Klausurtermin> zweiBeispielklausuren() {
    Klausurtermin klausurtermin = new Klausurtermin("Programmierung",
        LocalDate.of(2022, 3, 17),
        LocalTime.of(8, 30),
        LocalTime.of(9, 30),
        Klausurart.ONLINE,
        mock(VeranstaltungsId.class));
    Klausurtermin klausurtermin2 = new Klausurtermin("Programmierung",
        LocalDate.of(2022, 3, 19),
        LocalTime.of(8, 30),
        LocalTime.of(10, 30),
        Klausurart.PRAESENZ,
        mock(VeranstaltungsId.class));
    return List.of(klausurtermin, klausurtermin2);
  }
}
