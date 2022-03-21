package de.hhu.chicken.infrastructure.web.forms;

import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.infrastructure.web.forms.verification.stereotypes.IsValidDatum;
import de.hhu.chicken.infrastructure.web.forms.verification.stereotypes.IsValidId;
import de.hhu.chicken.infrastructure.web.forms.verification.stereotypes.IsValidOnlineKlausurZeitraum;
import de.hhu.chicken.infrastructure.web.forms.verification.stereotypes.IsValidPraesenzKlausurZeitraum;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

public class KlausurForm {

  @NotEmpty
  String fach;

  @NotNull
  @IsValidDatum
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  LocalDate datum;

  @NotNull
  @DateTimeFormat(pattern = "HH:mm")
  LocalTime von;

  @NotNull
  @DateTimeFormat(pattern = "HH:mm")
  LocalTime bis;

  Boolean isPraesenz;

  @NotNull
  @IsValidId
  Long veranstaltungsId;

  @IsValidPraesenzKlausurZeitraum
  private List<LocalTime> praesenzKlausurZeitraum;

  @IsValidOnlineKlausurZeitraum
  private List<LocalTime> onlineKlausurZeitraum;

  public KlausurForm(String fach, LocalDate datum, LocalTime von, LocalTime bis,
                     Boolean isPraesenz, Long veranstaltungsId) {
    this.fach = fach;
    this.datum = datum;
    this.von = von;
    this.bis = bis;
    this.isPraesenz = isPraesenz;
    this.veranstaltungsId = veranstaltungsId;

    if (von == null || bis == null) {
      return;
    }

    if (isPraesenz) {
      this.praesenzKlausurZeitraum = List.of(von, bis);
      return;
    }
    this.onlineKlausurZeitraum = List.of(von, bis);
  }

  public Klausur toKlausur() {
    return new Klausur(null,
        fach,
        datum,
        von,
        bis,
        isPraesenz,
        veranstaltungsId);
  }

  public LocalDate getDatum() {
    return datum;
  }

  public void setDatum(LocalDate datum) {
    this.datum = datum;
  }

  public LocalTime getVon() {
    return von;
  }

  public void setVon(LocalTime von) {
    this.von = von;
  }

  public LocalTime getBis() {
    return bis;
  }

  public void setBis(LocalTime bis) {
    this.bis = bis;
  }
}