package de.hhu.chicken.domain.klausur;

import static de.hhu.chicken.domain.klausur.Klausurart.ONLINE;
import static de.hhu.chicken.domain.klausur.Klausurart.PRAESENZ;

import java.time.LocalDate;
import java.time.LocalTime;

public record Klausurtermin(String fach, LocalDate datum, LocalTime von, LocalTime bis,
                            Klausurart klausurart, VeranstaltungsId veranstaltungsId) {

  public LocalTime berechneFreistellungsStartzeitpunkt() {
    LocalTime startzeitpunkt;
    if (klausurart.equals(ONLINE)) {
      startzeitpunkt = von.minusMinutes(30);
    } else {
      startzeitpunkt = von.minusHours(2);
    }
    if(startzeitpunkt.isBefore(LocalTime.of(8, 30))) {
      startzeitpunkt = LocalTime.of(8, 30);
    }
    return startzeitpunkt;
  }

  public LocalTime berechneFreistellungsEndzeitpunkt() {
    LocalTime endzeitpunkt = bis;
    if (klausurart.equals(PRAESENZ)) {
      endzeitpunkt = bis.plusHours(2);
    }
    if(endzeitpunkt.isAfter(LocalTime.of(13, 30))) {
      endzeitpunkt = LocalTime.of(13, 30);
    }
    return endzeitpunkt;
  }
}
