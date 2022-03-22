package de.hhu.chicken.domain.student;

import de.hhu.chicken.domain.stereotypes.ValueObject;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@ValueObject
public class Urlaubstermin {

  private final LocalDate datum;
  private final LocalTime von;
  private final LocalTime bis;

  Urlaubstermin(LocalDate datum, LocalTime von, LocalTime bis) {
    this.datum = datum;
    this.von = von;
    this.bis = bis;
  }

  public Duration dauer() {
    return Duration.between(von, bis);
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

  public boolean isStornierbar(LocalDate heute) {
    return heute.isBefore(datum);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Urlaubstermin that = (Urlaubstermin) o;
    return datum.equals(that.datum) && von.equals(that.von) && bis.equals(that.bis);
  }

  @Override
  public int hashCode() {
    return Objects.hash(datum, von, bis);
  }
}
