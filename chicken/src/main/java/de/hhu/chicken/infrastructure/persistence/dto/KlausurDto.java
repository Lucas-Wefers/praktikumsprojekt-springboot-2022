package de.hhu.chicken.infrastructure.persistence.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.annotation.Id;

public class KlausurDto {
  @Id
  UUID uuid;
  String fach;
  LocalDate datum;
  LocalTime von;
  LocalTime bis;
  boolean isPraesenz;
  Long veranstaltungsId;

  public KlausurDto(UUID uuid, String fach, LocalDate datum, LocalTime von, LocalTime bis,
                    boolean isPraesenz, Long veranstaltungsId) {
    this.uuid = uuid;
    this.fach = fach;
    this.datum = datum;
    this.von = von;
    this.bis = bis;
    this.isPraesenz = isPraesenz;
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

  public boolean isPraesenz() {
    return isPraesenz;
  }

  public Long getVeranstaltungsId() {
    return veranstaltungsId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KlausurDto that = (KlausurDto) o;
    return uuid.equals(that.uuid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid);
  }
}
