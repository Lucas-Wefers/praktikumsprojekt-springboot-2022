package de.hhu.chicken.service.klausurenservice;

import static de.hhu.chicken.service.klausurenservice.KlausurterminTemplates.zweiBeispielklausuren;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.hhu.chicken.domain.klausur.Klausurtermin;
import de.hhu.chicken.service.klausurservice.KlausurenService;
import de.hhu.chicken.service.repositories.KlausurterminRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class KlausurenServiceTest {

  @Test
  @DisplayName("Der Service ruft das Repository auf und gibt die richtigen Klausurtermine zurück")
  void test_1() {
    KlausurterminRepository repo = mock(KlausurterminRepository.class);
    KlausurenService service = new KlausurenService(repo);
    when(repo.alleKlausuren()).thenReturn(zweiBeispielklausuren());

    List<Klausurtermin> klausurtermine = service.alleKlausuren();

    verify(repo).alleKlausuren();
    assertThat(klausurtermine).isEqualTo(zweiBeispielklausuren());
  }
}
