package de.hhu.chicken.domain.klausur;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class KlausurTest {

  @Test
  @DisplayName("Bei einer Präsenzklausur um 10:30 wird ab 8:30 freigestellt")
  void test_1() {
    Klausur klausur = new Klausur("Programmierung",
        LocalDate.of(2022, 3, 12),
        LocalTime.of(10, 30),
        LocalTime.of(11, 30),
        true,
        1234L);

    LocalTime startzeitpunkt = klausur.berechneFreistellungsStartzeitpunkt();

    assertThat(startzeitpunkt).isEqualTo(LocalTime.of(8, 30));
  }

  @Test
  @DisplayName("Bei einer Präsenzklausur um 9:30 wird ab 8:30 freigestellt")
  void test_2() {
    Klausur klausur = new Klausur("Programmierung",
        LocalDate.of(2022, 3, 12),
        LocalTime.of(9, 30),
        LocalTime.of(11, 30),
        true,
        1234L);

    LocalTime startzeitpunkt = klausur.berechneFreistellungsStartzeitpunkt();

    assertThat(startzeitpunkt).isEqualTo(LocalTime.of(8, 30));
  }

  @Test
  @DisplayName("Bei einer Onlineklausur um 10:30 wird ab 10:00 freigestellt")
  void test_3() {
    Klausur klausur = new Klausur("Programmierung",
        LocalDate.of(2022, 3, 12),
        LocalTime.of(10, 30),
        LocalTime.of(11, 30),
        false,
        1234L);

    LocalTime startzeitpunkt = klausur.berechneFreistellungsStartzeitpunkt();

    assertThat(startzeitpunkt).isEqualTo(LocalTime.of(10, 0));
  }

  @Test
  @DisplayName("Bei einer Onlineklausur um 8:45 wird ab 8:30 freigestellt")
  void test_4() {
    Klausur klausur = new Klausur("Programmierung",
        LocalDate.of(2022, 3, 12),
        LocalTime.of(8, 45),
        LocalTime.of(11, 30),
        false,
        1234L);

    LocalTime startzeitpunkt = klausur.berechneFreistellungsStartzeitpunkt();

    assertThat(startzeitpunkt).isEqualTo(LocalTime.of(8, 30));
  }

  @Test
  @DisplayName("Bei einer Präsenzklausur die um 10:30 endet wird bis 12:30 freigestellt")
  void test_5() {
    Klausur klausur = new Klausur("Programmierung",
        LocalDate.of(2022, 3, 12),
        LocalTime.of(8, 30),
        LocalTime.of(10, 30),
        true,
        1234L);

    LocalTime endzeitpunkt = klausur.berechneFreistellungsEndzeitpunkt();

    assertThat(endzeitpunkt).isEqualTo(LocalTime.of(12, 30));
  }

  @Test
  @DisplayName("Bei einer Präsenzklausur die um 12:30 endet wird bis 13:30 freigestellt")
  void test_6() {
    Klausur klausur = new Klausur("Programmierung",
        LocalDate.of(2022, 3, 12),
        LocalTime.of(8, 30),
        LocalTime.of(12, 30),
        true,
        1234L);

    LocalTime endzeitpunkt = klausur.berechneFreistellungsEndzeitpunkt();

    assertThat(endzeitpunkt).isEqualTo(LocalTime.of(13, 30));
  }

  @Test
  @DisplayName("Bei einer Onlineklausur die um 12:30 endet wird bis 12:30 freigestellt")
  void test_7() {
    Klausur klausur = new Klausur("Programmierung",
        LocalDate.of(2022, 3, 12),
        LocalTime.of(8, 30),
        LocalTime.of(12, 30),
        false,
        1234L);

    LocalTime endzeitpunkt = klausur.berechneFreistellungsEndzeitpunkt();

    assertThat(endzeitpunkt).isEqualTo(LocalTime.of(12, 30));
  }

  @Test
  @DisplayName("Bei einer Onlineklausur die um 14:30 endet wird bis 13:30 freigestellt")
  void test_8() {
    Klausur klausur = new Klausur("Programmierung",
        LocalDate.of(2022, 3, 12),
        LocalTime.of(8, 30),
        LocalTime.of(14, 30),
        false,
        1234L);

    LocalTime endzeitpunkt = klausur.berechneFreistellungsEndzeitpunkt();

    assertThat(endzeitpunkt).isEqualTo(LocalTime.of(13, 30));
  }
}
