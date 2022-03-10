package de.hhu.chicken.infrastructure.persistence;

import static de.hhu.chicken.templates.KlausurTemplates.zweiBeispielklausuren;
import static de.hhu.chicken.templates.KlausurTemplates.zweiBeispielklausurenDtos;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.infrastructure.persistence.dao.KlausurDao;
import de.hhu.chicken.infrastructure.persistence.dto.KlausurDto;
import de.hhu.chicken.service.repositories.KlausurRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class KlausurRepositoryTest {

  @Test
  @DisplayName("Eine leere Datenbank enthält keine Klausuren")
  void test_1() {
    KlausurDao klausurDao = mock(KlausurDao.class);
    KlausurRepository repo = new KlausurRepositoryImpl(klausurDao);
    when(klausurDao.findAll()).thenReturn(List.of());

    List<Klausur> klausuren = repo.alleKlausuren();

    assertThat(klausuren).isEmpty();
    verify(klausurDao).findAll();
  }

  @Test
  @DisplayName("In der Db sind 2 Klausuren und alle Klausuren werden aus der Db geladen")
  void test_2() {
    KlausurDao klausurDao = mock(KlausurDao.class);
    KlausurRepository repo = new KlausurRepositoryImpl(klausurDao);
    List<KlausurDto> klausurDtos = zweiBeispielklausurenDtos();
    when(klausurDao.findAll()).thenReturn(klausurDtos);

    List<Klausur> klausuren = repo.alleKlausuren();

    assertThat(klausuren).isEqualTo(zweiBeispielklausuren());
    verify(klausurDao).findAll();
  }
}
