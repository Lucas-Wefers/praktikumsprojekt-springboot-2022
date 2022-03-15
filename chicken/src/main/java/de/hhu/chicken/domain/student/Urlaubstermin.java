package de.hhu.chicken.domain.student;

import de.hhu.chicken.domain.stereotypes.ValueObject;
import java.time.LocalDate;
import java.time.LocalTime;

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

  public LocalDate getDatum() {
    return datum;
  }

  public LocalTime getVon() {
    return von;
  }

  public LocalTime getBis() {
    return bis;
  }
}
