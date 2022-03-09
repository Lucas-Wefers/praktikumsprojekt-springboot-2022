package de.hhu.chicken.service.repositories;

import de.hhu.chicken.domain.klausur.Klausurtermin;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface KlausurterminRepository {

  List<Klausurtermin> alleKlausuren();
}
