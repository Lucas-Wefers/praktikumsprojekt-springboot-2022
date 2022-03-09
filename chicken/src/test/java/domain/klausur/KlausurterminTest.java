package domain.klausur;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import de.hhu.chicken.domain.klausur.Klausurart;
import de.hhu.chicken.domain.klausur.Klausurtermin;
import de.hhu.chicken.domain.klausur.VeranstaltungsId;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class KlausurterminTest {

  @Test
  @DisplayName("Bei einer Präsenzklausur um 10:30 wird ab 8:30 freigestellt")
  void test_1() {
    Klausurtermin klausurtermin = new Klausurtermin("Programmierung",
        LocalDate.of(2022, 3, 12),
        LocalTime.of(10, 30),
        LocalTime.of(11, 30),
        Klausurart.PRAESENZ,
        mock(VeranstaltungsId.class));

    LocalTime startzeitpunkt = klausurtermin.berechneFreistellungsStartzeitpunkt();

    assertThat(startzeitpunkt).isEqualTo(LocalTime.of(8, 30));
  }

  @Test
  @DisplayName("Bei einer Präsenzklausur um 9:30 wird ab 8:30 freigestellt")
  void test_2() {
    Klausurtermin klausurtermin = new Klausurtermin("Programmierung",
        LocalDate.of(2022, 3, 12),
        LocalTime.of(9, 30),
        LocalTime.of(11, 30),
        Klausurart.PRAESENZ,
        mock(VeranstaltungsId.class));

    LocalTime startzeitpunkt = klausurtermin.berechneFreistellungsStartzeitpunkt();

    assertThat(startzeitpunkt).isEqualTo(LocalTime.of(8, 30));
  }

  @Test
  @DisplayName("Bei einer Onlineklausur um 10:30 wird ab 10:00 freigestellt")
  void test_3() {
    Klausurtermin klausurtermin = new Klausurtermin("Programmierung",
        LocalDate.of(2022, 3, 12),
        LocalTime.of(10, 30),
        LocalTime.of(11, 30),
        Klausurart.ONLINE,
        mock(VeranstaltungsId.class));

    LocalTime startzeitpunkt = klausurtermin.berechneFreistellungsStartzeitpunkt();

    assertThat(startzeitpunkt).isEqualTo(LocalTime.of(10, 0));
  }

  @Test
  @DisplayName("Bei einer Onlineklausur um 8:45 wird ab 8:30 freigestellt")
  void test_4() {
    Klausurtermin klausurtermin = new Klausurtermin("Programmierung",
        LocalDate.of(2022, 3, 12),
        LocalTime.of(8, 45),
        LocalTime.of(11, 30),
        Klausurart.ONLINE,
        mock(VeranstaltungsId.class));

    LocalTime startzeitpunkt = klausurtermin.berechneFreistellungsStartzeitpunkt();

    assertThat(startzeitpunkt).isEqualTo(LocalTime.of(8, 30));
  }

  @Test
  @DisplayName("Bei einer Präsenzklausur die um 10:30 endet wird bis 12:30 freigestellt")
  void test_5() {
    Klausurtermin klausurtermin = new Klausurtermin("Programmierung",
        LocalDate.of(2022, 3, 12),
        LocalTime.of(8, 30),
        LocalTime.of(10, 30),
        Klausurart.PRAESENZ,
        mock(VeranstaltungsId.class));

    LocalTime endzeitpunkt = klausurtermin.berechneFreistellungsEndzeitpunkt();

    assertThat(endzeitpunkt).isEqualTo(LocalTime.of(12, 30));
  }

  @Test
  @DisplayName("Bei einer Präsenzklausur die um 12:30 endet wird bis 13:30 freigestellt")
  void test_6() {
    Klausurtermin klausurtermin = new Klausurtermin("Programmierung",
        LocalDate.of(2022, 3, 12),
        LocalTime.of(8, 30),
        LocalTime.of(12, 30),
        Klausurart.PRAESENZ,
        mock(VeranstaltungsId.class));

    LocalTime endzeitpunkt = klausurtermin.berechneFreistellungsEndzeitpunkt();

    assertThat(endzeitpunkt).isEqualTo(LocalTime.of(13, 30));
  }

  @Test
  @DisplayName("Bei einer Onlineklausur die um 12:30 endet wird bis 12:30 freigestellt")
  void test_7() {
    Klausurtermin klausurtermin = new Klausurtermin("Programmierung",
        LocalDate.of(2022, 3, 12),
        LocalTime.of(8, 30),
        LocalTime.of(12, 30),
        Klausurart.ONLINE,
        mock(VeranstaltungsId.class));

    LocalTime endzeitpunkt = klausurtermin.berechneFreistellungsEndzeitpunkt();

    assertThat(endzeitpunkt).isEqualTo(LocalTime.of(12, 30));
  }

  @Test
  @DisplayName("Bei einer Onlineklausur die um 14:30 endet wird bis 13:30 freigestellt")
  void test_8() {
    Klausurtermin klausurtermin = new Klausurtermin("Programmierung",
        LocalDate.of(2022, 3, 12),
        LocalTime.of(8, 30),
        LocalTime.of(14, 30),
        Klausurart.ONLINE,
        mock(VeranstaltungsId.class));

    LocalTime endzeitpunkt = klausurtermin.berechneFreistellungsEndzeitpunkt();

    assertThat(endzeitpunkt).isEqualTo(LocalTime.of(13, 30));
  }
}
