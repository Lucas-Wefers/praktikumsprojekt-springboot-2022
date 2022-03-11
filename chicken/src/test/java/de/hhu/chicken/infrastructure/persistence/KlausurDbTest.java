package de.hhu.chicken.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.service.repositories.KlausurRepository;
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

}
