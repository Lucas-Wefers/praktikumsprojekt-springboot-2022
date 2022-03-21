package de.hhu.chicken.infrastructure.web.controllers.student;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.service.klausurservice.KlausurService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
public class KlausurControllerTest {

  @Autowired
  MockMvc mvc;

  @MockBean
  KlausurService klausurService;

  @Test
  @DisplayName("Die richtige Seite fuer das Eintragen von Klausuren wird aufgerufen und ist "
      + "erreichbar")
  void test_1() throws Exception {
    mvc.perform(get("/klausuren"))
        .andExpect(status().isOk())
        .andExpect(view().name("klausurEintragung"));
  }

  @Test
  @DisplayName("Beim Absenden einer invaliden Klausurform wird nichts gespeichert und es erfolgt "
      + "kein redirect")
  void test_2() throws Exception {
    mvc.perform(post("/klausuren")
            .param("fach", "Programmierung")
            .param("datum", "2022-03-19")
            .param("von", "08:30")
            .param("bis", "10:30")
            .param("isPraesenz", "true")
            .param("veranstaltungsId", "12")
        )
        .andExpect(status().isOk())
        .andExpect(view().name("klausurEintragung"));
    verify(klausurService, times(0)).klausurSpeichern(any());
  }

  @Test
  @DisplayName("Beim Absenden einer validen Klausurform wird die Klausur gespeichert und es "
      + "erfolgt ein redirect")
  void test_3() throws Exception {
    Klausur klausur = new Klausur(null,
        "Programmierung",
        LocalDate.of(2022, 3, 18),
        LocalTime.of(8, 30),
        LocalTime.of(10, 30),
        true,
        1234L);

    mvc.perform(post("/klausuren")
            .param("fach", "Programmierung")
            .param("datum", "2022-03-18")
            .param("von", "08:30")
            .param("bis", "10:30")
            .param("isPraesenz", "true")
            .param("veranstaltungsId", "1234")
        )
        .andExpect(status().isOk())
        .andExpect(view().name("klausurErfolg"));
    verify(klausurService, times(1)).klausurSpeichern(klausur);
  }

  @Test
  @DisplayName("Die Klausuren werden an das Model weitergeleitet und die richtige Seite ist"
     + " erreichbar")
  void test_4() throws Exception {
    Klausur klausur1 = new Klausur(1L,
        "Programmierung",
        LocalDate.of(2022, 3, 19),
        LocalTime.of(8, 30),
        LocalTime.of(10, 30),
        true,
        1234L);
    Klausur klausur2 = new Klausur(2L,
        "Programmierung 2",
        LocalDate.of(2022, 3, 30),
        LocalTime.of(8, 30),
        LocalTime.of(10, 30),
        true,
        1234L);
    List<Klausur> klausuren = List.of(klausur1, klausur2);
    when(klausurService.alleKlausuren()).thenReturn(klausuren);

    mvc.perform(get("/klausuranmeldung"))
        .andExpect(status().isOk())
        .andExpect(view().name("klausurAnmeldung"))
        .andExpect(model().attribute("klausuren", klausuren));
  }
}
