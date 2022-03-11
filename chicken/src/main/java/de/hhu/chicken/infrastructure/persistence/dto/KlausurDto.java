package de.hhu.chicken.infrastructure.persistence.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import org.springframework.data.annotation.Id;

public class KlausurDto {
  @Id
  private final Long id;
  private final String fach;
  private final LocalDate datum;
  private final LocalTime von;
  private final LocalTime bis;
  private final boolean isPraesenz;
  private final Long veranstaltungsId;

  public KlausurDto(Long id, String fach, LocalDate datum, LocalTime von, LocalTime bis,
      boolean isPraesenz, Long veranstaltungsId) {
    this.id = id;
    this.fach = fach;
    this.datum = datum;
    this.von = von;
    this.bis = bis;
    this.isPraesenz = isPraesenz;
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
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
