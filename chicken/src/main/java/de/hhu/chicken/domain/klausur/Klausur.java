package de.hhu.chicken.domain.klausur;

import static de.hhu.chicken.domain.klausur.Klausurart.ONLINE;
import static de.hhu.chicken.domain.klausur.Klausurart.PRAESENZ;

import de.hhu.chicken.domain.stereotypes.AggregateRoot;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@AggregateRoot
public class Klausur {

  private final UUID uuid;
  private final String fach;
  private final LocalDate datum;
  private final LocalTime von;
  private final LocalTime bis;
  private final Klausurart klausurart;
  private final VeranstaltungsId veranstaltungsId;

  public Klausur(String fach, LocalDate datum, LocalTime von, LocalTime bis,
      boolean isPraesenz, Long veranstaltungsId) {
    this.uuid = UUID.randomUUID();
    this.fach = fach;
    this.datum = datum;
    this.von = von;
    this.bis = bis;
    this.klausurart = isPraesenz ? PRAESENZ : ONLINE;
    this.veranstaltungsId = new VeranstaltungsId(veranstaltungsId);
  }

  public Klausur(UUID uuid, String fach, LocalDate datum, LocalTime von, LocalTime bis,
                 boolean isPraesenz, Long veranstaltungsId) {
    this.uuid = uuid;
    this.fach = fach;
    this.datum = datum;
    this.von = von;
    this.bis = bis;
    this.klausurart = isPraesenz ? PRAESENZ : ONLINE;
    this.veranstaltungsId = new VeranstaltungsId(veranstaltungsId);
  }

  public LocalTime berechneFreistellungsStartzeitpunkt() {
    LocalTime startzeitpunkt;
    if (klausurart.equals(ONLINE)) {
      startzeitpunkt = von.minusMinutes(30);
    } else {
      startzeitpunkt = von.minusHours(2);
    }
    if (startzeitpunkt.isBefore(LocalTime.of(8, 30))) {
      startzeitpunkt = LocalTime.of(8, 30);
    }
    return startzeitpunkt;
  }

  public LocalTime berechneFreistellungsEndzeitpunkt() {
    LocalTime endzeitpunkt = bis;
    if (klausurart.equals(PRAESENZ)) {
      endzeitpunkt = bis.plusHours(2);
    }
    if (endzeitpunkt.isAfter(LocalTime.of(13, 30))) {
      endzeitpunkt = LocalTime.of(13, 30);
    }
    return endzeitpunkt;
  }

  public UUID getUuid() {
    return uuid;
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
    return veranstaltungsId.getId();
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
    return uuid.equals(klausur.uuid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid);
  }

  @Override
  public String toString() {
    return "Klausur{" +
        "uuid=" + uuid +
        ", fach='" + fach + '\'' +
        ", datum=" + datum +
        ", von=" + von +
        ", bis=" + bis +
        ", klausurart=" + klausurart +
        ", veranstaltungsId=" + veranstaltungsId +
        '}';
  }
}
