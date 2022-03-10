package de.hhu.chicken.infrastructure.persistence.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

public class KlausurDto {
  @Id
  UUID uuid;
  String fach;
  LocalDate datum;
  LocalTime von;
  LocalTime bis;
  int klausurart;
  Long veranstaltungsId;

  public KlausurDto(UUID uuid, String fach, LocalDate datum, LocalTime von, LocalTime bis,
                    int klausurart, Long veranstaltungsId) {
    this.uuid = uuid;
    this.fach = fach;
    this.datum = datum;
    this.von = von;
    this.bis = bis;
    this.klausurart = klausurart;
    this.veranstaltungsId = veranstaltungsId;
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

  public int getKlausurart() {
    return klausurart;
  }

  public Long getVeranstaltungsId() {
    return veranstaltungsId;
  }
}
