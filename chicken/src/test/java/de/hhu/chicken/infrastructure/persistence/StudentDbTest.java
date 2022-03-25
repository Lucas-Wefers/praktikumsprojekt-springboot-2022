package de.hhu.chicken.infrastructure.persistence;

import static de.hhu.chicken.templates.KlausurTemplates.beispielklausur;
import static org.assertj.core.api.Assertions.assertThat;

import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.domain.student.Student;
import de.hhu.chicken.service.repositories.KlausurRepository;
import de.hhu.chicken.service.repositories.StudentRepository;
import java.time.LocalDate;
import java.time.LocalTime;
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
  private KlausurRepository klausurRepository;

  @Autowired
  private StudentRepository studentRepository;

  @Test
  @DisplayName("In der leeren Datenbank gibt es keinen Studenten mit Github-Id 12345678L")
  void test_1() {
    Student studentByGithubId = studentRepository.findStudentByGithubId(12345678L);

    assertThat(studentByGithubId).isNull();
  }

  @Test
  @DisplayName("Ein Student mit einer Klausur und der Github-Id 12345678L wird in die " +
      "Datenbank gespeichert und wieder ausgelesen")
  void test_2() {
    Student student = new Student(12345678L, "jensbendisposto");
    Klausur klausur = beispielklausur();
    student.fuegeKlausurHinzu(klausur.getId(),
        klausur.getDatum(),
        klausur.berechneFreistellungsStartzeitpunkt(LocalTime.of(9, 30)),
        klausur.berechneFreistellungsEndzeitpunkt(LocalTime.of(13, 30)));
    studentRepository.studentSpeichern(student);

    Student studentByGithubId = studentRepository.findStudentByGithubId(12345678L);

    assertThat(studentByGithubId).isEqualTo(student);
    assertThat(studentByGithubId.getKlausurReferenzen()).isEqualTo(student.getKlausurReferenzen());
  }

  @Test
  @DisplayName("Ein Student mit einem Urlaub und der Github-Id 12345678L wird in die " +
      "Datenbank gespeichert und wieder ausgelesen")
  void test_3() {
    Student student = new Student(12345678L, "jensbendisposto");
    student.fuegeUrlaubsterminHinzu(LocalDate.of(2022, 3, 22),
        LocalTime.of(9, 30),
        LocalTime.of(13, 30),
        false,
        LocalTime.of(9, 30),
        LocalTime.of(13, 30));
    studentRepository.studentSpeichern(student);

    Student studentByGithubId = studentRepository.findStudentByGithubId(12345678L);

    assertThat(studentByGithubId).isEqualTo(student);
    assertThat(studentByGithubId.getUrlaubstermine()).isEqualTo(student.getUrlaubstermine());
  }

  @Test
  @DisplayName("Zwei Studenten, mit den Github-Ids 12345678L und 23123187L werden in die " +
      "Datenbank gespeichert und der zweite wird wieder ausgelesen")
  void test_4() {
    Student student = new Student(12345678L, "jensbendisposto");
    Student student2 = new Student(23123187L, "christianmeter");
    studentRepository.studentSpeichern(student);
    studentRepository.studentSpeichern(student2);

    Student studentByGithubId = studentRepository.findStudentByGithubId(23123187L);

    assertThat(studentByGithubId).isEqualTo(student2);
  }

  @Test
  @DisplayName("Ein Student wird ausgelesen und ihm wird eine KLausur hinzugefuegt und " +
      "anschliessend in der Datenbank gespeichert")
  void test_5() {
    Student student = new Student(23123187L, "christianmeter");
    studentRepository.studentSpeichern(student);
    Student studentByGithubId = studentRepository.findStudentByGithubId(23123187L);
    Klausur klausur = beispielklausur();
    studentByGithubId.fuegeKlausurHinzu(klausur.getId(),
        klausur.getDatum(),
        klausur.berechneFreistellungsStartzeitpunkt(LocalTime.of(9, 30)),
        klausur.berechneFreistellungsEndzeitpunkt(LocalTime.of(13, 30)));
    studentRepository.studentSpeichern(studentByGithubId);

    Student studentByGithubIdUpdated = studentRepository.findStudentByGithubId(23123187L);

    assertThat(studentByGithubIdUpdated).isEqualTo(studentByGithubId);
    assertThat(studentByGithubIdUpdated.getKlausurReferenzen())
        .isEqualTo(studentByGithubId.getKlausurReferenzen());
  }
}
