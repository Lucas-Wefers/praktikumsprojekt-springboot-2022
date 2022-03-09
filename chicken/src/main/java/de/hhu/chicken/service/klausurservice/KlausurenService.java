package de.hhu.chicken.service.klausurservice;

import de.hhu.chicken.domain.klausur.Klausurtermin;
import de.hhu.chicken.service.repositories.KlausurterminRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class KlausurenService {

  KlausurterminRepository repo;

  public KlausurenService(
      KlausurterminRepository repo) {
    this.repo = repo;
  }

  public List<Klausurtermin> alleKlausuren() {
    return repo.alleKlausuren();
  }

  public void klausurSpeichern(Klausurtermin klausurtermin) {
    repo.klausurSpeichern(klausurtermin);
  }
}
