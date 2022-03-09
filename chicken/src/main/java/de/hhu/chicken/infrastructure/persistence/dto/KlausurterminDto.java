package de.hhu.chicken.infrastructure.persistence.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

public class KlausurterminDto {
  @Id
  Long id;

  @NotNull
  String fach;

  @NotNull
  LocalDate datum;

  @NotNull
  LocalTime von;

  @NotNull
  LocalTime bis;

  @NotNull
  boolean klausurart;

  @NotNull
  Long veranstaltungsId;

  public KlausurterminDto(String fach, LocalDate datum, LocalTime von, LocalTime bis,
      boolean klausurart, Long veranstaltungsId) {
    this.fach = fach;
    this.datum = datum;
    this.von = von;
    this.bis = bis;
    this.klausurart = klausurart;
    this.veranstaltungsId = veranstaltungsId;
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

  public boolean isKlausurart() {
    return klausurart;
  }

  public Long getVeranstaltungsId() {
    return veranstaltungsId;
  }
}
