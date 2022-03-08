package de.hhu.chicken.domain.klausur;

import java.time.LocalDateTime;

public record Klausurtermin(String fach, LocalDateTime von, LocalDateTime bis,
                            Klausurart klausurart, VeranstaltungsId veranstaltungsId) {

}
