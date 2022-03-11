package de.hhu.chicken.infrastructure.persistence;

import static de.hhu.chicken.templates.KlausurTemplates.beispielklausur;
import static de.hhu.chicken.templates.KlausurTemplates.beispielklausurDto;
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
import java.util.Optional;
import java.util.UUID;
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

  @Test
  @DisplayName("Beim Speichern einer Klausur wird die save Methode im Dao aufgerufen")
  void test_3() {
    Klausur klausur = beispielklausur();
    KlausurDto klausurDto = beispielklausurDto();
    KlausurDao klausurDao = mock(KlausurDao.class);
    KlausurRepository repo = new KlausurRepositoryImpl(klausurDao);

    repo.klausurSpeichern(klausur);

    verify(klausurDao).save(klausurDto);
  }

  @Test
  @DisplayName("Beim Suchen einer nicht existierenden uuid wird null returned")
  void test_4() {
    KlausurDao klausurDao = mock(KlausurDao.class);
    KlausurRepository repo = new KlausurRepositoryImpl(klausurDao);
    UUID uuid = UUID.randomUUID();
    when(klausurDao.findByUuid(uuid)).thenReturn(Optional.empty());

    Klausur klausur = repo.findKlausurByUuid(uuid);

    assertThat(klausur).isNull();
    verify(klausurDao).findByUuid(uuid);
  }

  @Test
  @DisplayName("Beim Suchen einer existierenden uuid wird eine Klausur returned")
  void test_5() {
    KlausurDao klausurDao = mock(KlausurDao.class);
    KlausurRepository repo = new KlausurRepositoryImpl(klausurDao);
    Klausur klausur = beispielklausur();
    KlausurDto klausurDto = beispielklausurDto();
    when(klausurDao.findByUuid(klausur.getUuid())).thenReturn(Optional.of(klausurDto));

    Klausur klausurByUuid = repo.findKlausurByUuid(klausur.getUuid());

    assertThat(klausurByUuid).isEqualTo(klausur);
    verify(klausurDao).findByUuid(klausur.getUuid());
  }
}
