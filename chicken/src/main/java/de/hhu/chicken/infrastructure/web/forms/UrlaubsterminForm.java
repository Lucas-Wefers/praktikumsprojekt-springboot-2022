package de.hhu.chicken.infrastructure.web.forms;

import de.hhu.chicken.infrastructure.web.forms.verification.stereotypes.IsValidDatum;
import de.hhu.chicken.infrastructure.web.forms.verification.stereotypes.IsVielfacheVon15Minuten;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

public record UrlaubsterminForm(
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @IsValidDatum
    @NotNull
    LocalDate datum,

    @DateTimeFormat(pattern = "HH:mm")
    @IsVielfacheVon15Minuten
    @NotNull
    LocalTime von,

    @DateTimeFormat(pattern = "HH:mm")
    @IsVielfacheVon15Minuten
    @NotNull
    LocalTime bis
) {

}