package de.hhu.chicken.service.repositories;

import de.hhu.chicken.domain.klausur.Klausur;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface KlausurRepository {

  List<Klausur> alleKlausuren();

  void klausurSpeichern(Klausur klausur);

  Klausur findKlausurByUuid(UUID uuid);
}
