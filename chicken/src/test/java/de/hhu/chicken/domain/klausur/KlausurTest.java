package de.hhu.chicken.domain.klausur;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class KlausurTest {

  @Test
  @DisplayName("Bei einer Praesenzklausur um 10:30 wird ab 9:30 freigestellt")
  void test_1() {
    Klausur klausur = beispielKlausur(10, 30, 11, true);

    LocalTime startzeitpunkt = klausur.berechneFreistellungsStartzeitpunkt();

    assertThat(startzeitpunkt).isEqualTo(LocalTime.of(9, 30));
  }

  @Test
  @DisplayName("Bei einer Praesenzklausur um 9:30 wird ab 9:30 freigestellt")
  void test_2() {
    Klausur klausur = beispielKlausur(9, 30, 11, true);

    LocalTime startzeitpunkt = klausur.berechneFreistellungsStartzeitpunkt();

    assertThat(startzeitpunkt).isEqualTo(LocalTime.of(9, 30));
  }

  @Test
  @DisplayName("Bei einer Onlineklausur um 10:30 wird ab 10:00 freigestellt")
  void test_3() {
    Klausur klausur = beispielKlausur(10, 30, 11, false);

    LocalTime startzeitpunkt = klausur.berechneFreistellungsStartzeitpunkt();

    assertThat(startzeitpunkt).isEqualTo(LocalTime.of(10, 0));
  }

  @Test
  @DisplayName("Bei einer Onlineklausur um 8:45 wird ab 9:30 freigestellt")
  void test_4() {
    Klausur klausur = beispielKlausur(8, 45, 11, false);

    LocalTime startzeitpunkt = klausur.berechneFreistellungsStartzeitpunkt();

    assertThat(startzeitpunkt).isEqualTo(LocalTime.of(9, 30));
  }

  @Test
  @DisplayName("Bei einer Praesenzklausur die um 10:30 endet wird bis 12:30 freigestellt")
  void test_5() {
    Klausur klausur = beispielKlausur(8, 30, 10, true);

    LocalTime endzeitpunkt = klausur.berechneFreistellungsEndzeitpunkt();

    assertThat(endzeitpunkt).isEqualTo(LocalTime.of(12, 30));
  }

  @Test
  @DisplayName("Bei einer Praesenzklausur die um 12:30 endet wird bis 13:30 freigestellt")
  void test_6() {
    Klausur klausur = beispielKlausur(8, 30, 12, true);

    LocalTime endzeitpunkt = klausur.berechneFreistellungsEndzeitpunkt();

    assertThat(endzeitpunkt).isEqualTo(LocalTime.of(13, 30));
  }

  @Test
  @DisplayName("Bei einer Onlineklausur die um 12:30 endet wird bis 12:30 freigestellt")
  void test_7() {
    Klausur klausur = beispielKlausur(8, 30, 12, false);

    LocalTime endzeitpunkt = klausur.berechneFreistellungsEndzeitpunkt();

    assertThat(endzeitpunkt).isEqualTo(LocalTime.of(12, 30));
  }

  @Test
  @DisplayName("Bei einer Onlineklausur die um 14:30 endet wird bis 13:30 freigestellt")
  void test_8() {
    Klausur klausur = beispielKlausur(8, 30, 14, false);

    LocalTime endzeitpunkt = klausur.berechneFreistellungsEndzeitpunkt();

    assertThat(endzeitpunkt).isEqualTo(LocalTime.of(13, 30));
  }

  private Klausur beispielKlausur(int startStunde, int startMinute, int endStunde, boolean isPraesenz) {
    return new Klausur(1L,
        "Programmierung",
        LocalDate.of(2022, 3, 12),
        LocalTime.of(startStunde, startMinute),
        LocalTime.of(endStunde, 30),
        isPraesenz,
        1234L);
  }
}
