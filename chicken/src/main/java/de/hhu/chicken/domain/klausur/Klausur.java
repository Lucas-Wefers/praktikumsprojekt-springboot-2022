package de.hhu.chicken.domain.klausur;

import static de.hhu.chicken.domain.klausur.Klausurart.ONLINE;
import static de.hhu.chicken.domain.klausur.Klausurart.PRAESENZ;

import de.hhu.chicken.domain.stereotypes.AggregateRoot;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@AggregateRoot
public class Klausur {

  private final Long id;
  private final String fach;
  private final LocalDate datum;
  private final LocalTime von;
  private final LocalTime bis;
  private final Klausurart klausurart;
  private final VeranstaltungsId veranstaltungsId;

  private final LocalTime praktikumStart = LocalTime.of(9, 30);
  private final LocalTime praktikumEnde = LocalTime.of(13, 30);

  public Klausur(Long id, String fach, LocalDate datum, LocalTime von, LocalTime bis,
                 boolean isPraesenz, Long veranstaltungsId) {
    this.id = id;
    this.fach = fach;
    this.datum = datum;
    this.von = von;
    this.bis = bis;
    this.klausurart = isPraesenz ? PRAESENZ : ONLINE;
    this.veranstaltungsId = new VeranstaltungsId(veranstaltungsId);
  }

  public LocalTime berechneFreistellungsStartzeitpunkt() {
    LocalTime freistellungsStartzeitpunkt = von;

    if (klausurart == PRAESENZ) {
      if (freistellungsStartzeitpunkt.minusHours(2L).isBefore(freistellungsStartzeitpunkt)) {
        freistellungsStartzeitpunkt = freistellungsStartzeitpunkt.minusHours(2L);
      }
    } else {
      if (freistellungsStartzeitpunkt.minusMinutes(30L).isBefore(freistellungsStartzeitpunkt)) {
        freistellungsStartzeitpunkt = freistellungsStartzeitpunkt.minusMinutes(30L);
      }
    }
    if (freistellungsStartzeitpunkt.isBefore(praktikumStart)) {
      return praktikumStart;
    }

    return freistellungsStartzeitpunkt;
  }

  public LocalTime berechneFreistellungsEndzeitpunkt() {
    LocalTime endzeitpunkt = bis;

    if (klausurart == PRAESENZ) {
      if (endzeitpunkt.plusHours(2L).isAfter(endzeitpunkt)) {
        endzeitpunkt = endzeitpunkt.plusHours(2L);
      }
    }
    if (endzeitpunkt.isAfter(praktikumEnde)) {
      return praktikumEnde;
    }
    return endzeitpunkt;
  }

  public boolean isStornierbar(LocalDate heute) {
    return heute.isBefore(datum);
  }

  public Long getId() {
    return id;
  }

  public String getFach() {
    return fach;
  }

  public LocalDate getDatum() {
    return datum;
  }

  public LocalTime getVon() {
    return von;
  }

  public LocalTime getBis() {
    return bis;
  }

  public boolean isPraesenz() {
    return klausurart.equals(PRAESENZ);
  }

  public Long getVeranstaltungsId() {
    return veranstaltungsId.veranstaltungsId();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Klausur klausur = (Klausur) o;
    return Objects.equals(id, klausur.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return fach + " (" + datum.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ", "
        + von.format(DateTimeFormatter.ofPattern("HH:mm")) + " - "
        + bis.format(DateTimeFormatter.ofPattern("HH:mm")) + " Uhr)";
  }
}
