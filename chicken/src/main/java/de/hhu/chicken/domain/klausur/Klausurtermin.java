package de.hhu.chicken.domain.klausur;

import java.time.LocalDate;
import java.time.LocalTime;

public record Klausurtermin(String fach, LocalDate datum, LocalTime von, LocalTime bis,
                            Klausurart klausurart, VeranstaltungsId veranstaltungsId) {

}
