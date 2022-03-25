package de.hhu.chicken.service.repositories;

import de.hhu.chicken.domain.klausur.Klausur;
import java.util.List;

public interface KlausurRepository {

  List<Klausur> alleKlausuren();

  Klausur klausurSpeichern(Klausur klausur);

  Klausur findKlausurById(Long id);
}
