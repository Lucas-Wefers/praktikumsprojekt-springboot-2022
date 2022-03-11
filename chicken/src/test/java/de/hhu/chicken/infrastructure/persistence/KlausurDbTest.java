package de.hhu.chicken.infrastructure.persistence;

import static de.hhu.chicken.templates.KlausurTemplates.beispielklausur;
import static org.assertj.core.api.Assertions.assertThat;

import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.service.repositories.KlausurRepository;
import de.hhu.chicken.templates.KlausurTemplates;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles("test")
@Sql({"classpath:db/migration/V1__init.sql"})
public class KlausurDbTest {

  @Autowired
  KlausurRepository db;

  @Test
  @DisplayName("In der leeren Datenbank gibt es keine Klausuren")
  void test_1() {
    List<Klausur> klausuren = db.alleKlausuren();

    assertThat(klausuren).isEmpty();
  }

  @Test
  @DisplayName("Eine Klausur wird in die Datenbank gespeichert und ausgelesen")
  void test_2() {
    Klausur klausur = new Klausur(null,
        "Programmierung",
        LocalDate.of(2022, 3, 17),
        LocalTime.of(8, 30),
        LocalTime.of(9, 30),
        false,
        1234L);

    Klausur gespeicherteKlausur = db.klausurSpeichern(klausur);
    List<Klausur> klausuren = db.alleKlausuren();

    assertThat(klausuren).hasSize(1);
    assertThat(klausuren.get(0)).isEqualTo(gespeicherteKlausur);
  }

  @Test
  @DisplayName("Speichere 2 Klausuren in der Datenbank und lade alle Klausuren")
  void test_3() {
    Klausur klausur = new Klausur(null,
        "Programmierung",
        LocalDate.of(2022, 3, 17),
        LocalTime.of(8, 30),
        LocalTime.of(9, 30),
        false,
        1234L);

    Klausur gespeicherteKlausur1 = db.klausurSpeichern(klausur);
    Klausur gespeicherteKlausur2 = db.klausurSpeichern(klausur);
    List<Klausur> klausuren = db.alleKlausuren();

    assertThat(klausuren).hasSize(2);
  }

  @Test
  @DisplayName("Speichere 2 Klausuren in der Datenbank und lese die Klausur mit der Id 2 aus")
  void test_4() {
    Klausur klausur = new Klausur(null,
        "Programmierung",
        LocalDate.of(2022, 3, 17),
        LocalTime.of(8, 30),
        LocalTime.of(9, 30),
        false,
        1234L);

    Klausur gespeicherteKlausur1 = db.klausurSpeichern(klausur);
    Klausur gespeicherteKlausur2 = db.klausurSpeichern(klausur);
    Klausur klausurById = db.findKlausurById(gespeicherteKlausur2.getId());

    assertThat(klausurById).isEqualTo(gespeicherteKlausur2);
  }
}
