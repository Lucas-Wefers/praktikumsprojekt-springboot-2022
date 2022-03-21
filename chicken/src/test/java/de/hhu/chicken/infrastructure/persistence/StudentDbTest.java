package de.hhu.chicken.infrastructure.persistence;

import static de.hhu.chicken.templates.KlausurTemplates.beispielklausur;
import static org.assertj.core.api.Assertions.assertThat;

import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.domain.student.Student;
import de.hhu.chicken.service.repositories.KlausurRepository;
import de.hhu.chicken.service.repositories.StudentRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles("test")
@Sql({"classpath:db/migration/V1__init.sql",
    "classpath:db/migration/V2__create_student_tables.sql"})
public class StudentDbTest {

  @Autowired
  KlausurRepository klausurRepository;

  @Autowired
  StudentRepository studentRepository;

  @Test
  @DisplayName("In der leeren Datenbank gibt es keinen Studenten mit Github-Id 12345678L")
  void test_1() {
    Student studentByGithubId = studentRepository.findStudentByGithubId(12345678L);

    assertThat(studentByGithubId).isNull();
  }

  @Test
  @DisplayName("Ein Student mit der Github-Id 12345678L wird in die Datenbank gespeichert und" +
      " wieder ausgelesen")
  void test_2() {
    Student student = new Student(12345678L,
        "jensbendisposto");
    Klausur klausur = beispielklausur();
    student.fuegeKlausurHinzu(klausur.getId(),
        klausur.getDatum(),
        klausur.berechneFreistellungsStartzeitpunkt(),
        klausur.berechneFreistellungsEndzeitpunkt());
    studentRepository.studentSpeichern(student);

    Student studentByGithubId = studentRepository.findStudentByGithubId(12345678L);

    assertThat(studentByGithubId).isEqualTo(student);
    assertThat(studentByGithubId.getKlausurReferenzen()).isEqualTo(student.getKlausurReferenzen());
  }
}
