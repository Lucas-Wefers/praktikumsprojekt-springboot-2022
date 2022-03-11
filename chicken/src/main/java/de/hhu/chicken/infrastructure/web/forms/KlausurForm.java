package de.hhu.chicken.infrastructure.web.forms;

import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.infrastructure.web.forms.verification.IsValidId;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

public record KlausurForm(
    @NotEmpty
    String fach,

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate datum,

    @NotNull
    LocalTime von,

    @NotNull
    LocalTime bis,

    Boolean isPraesenz,

    @NotNull
    @IsValidId
    Long veranstaltungsId

) {

  public Klausur toKlausur() {
    return new Klausur(null,
        fach,
        datum,
        von,
        bis,
        isPraesenz != null,
        veranstaltungsId);
  }
}