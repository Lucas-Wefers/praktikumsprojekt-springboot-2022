package de.hhu.chicken.infrastructure.web.controllers.student;

import static de.hhu.chicken.infrastructure.web.configuration.AuthenticationTemplates.studentSession;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import de.hhu.chicken.configuration.PraktikumsUhrzeitConfiguration;
import de.hhu.chicken.service.klausurservice.KlausurService;
import de.hhu.chicken.service.studentservice.StudentService;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@ActiveProfiles("test")
public class UrlaubsterminControllerTest {

  @Autowired
  MockMvc mvc;

  @MockBean
  KlausurService klausurService;

  @MockBean
  StudentService studentService;

  @MockBean
  PraktikumsUhrzeitConfiguration uhrzeitConfiguration;

  @Test
  @DisplayName("Die richtige Seite fuer das Eintragen von Urlaubsterminen wird aufgerufen und ist "
      + "erreichbar")
  void test_1() throws Exception {
    MockHttpSession session = studentSession();

    mvc.perform(get("/urlaubsterminanmeldung")
            .session(session))
        .andExpect(status().isOk())
        .andExpect(view().name("urlaubsterminAnmeldung"));
  }

  @Test
  @DisplayName("Ein gueltiger Urlaubstermin wird eingetragen, der StudentService wird aufgerufen "
      + " und es wird weitergeleitet")
  void test_2() throws Exception {
    MockHttpSession session = studentSession();

    mvc.perform(post("/urlaubsterminanmeldung")
            .session(session)
            .param("datum", "2022-03-23")
            .param("von", "09:30")
            .param("bis", "13:30")
            .with(csrf())
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"));
    verify(studentService).urlaubsterminAnmelden(28324332L,
        "christianmeter",
        LocalDate.of(2022, 3, 23),
        LocalTime.of(9, 30),
        LocalTime.of(13, 30));
  }

  @Test
  @DisplayName("Ein ungueltiger Urlaubstermin wird nicht eingetragen, der  und es wird nicht " +
      "weitergeleitet")
  void test_3() throws Exception {
    MockHttpSession session = studentSession();

    mvc.perform(post("/urlaubsterminanmeldung")
            .session(session)
            .param("datum", "2022-03-23")
            .param("von", "09:30")
            .param("bis", "08:30")
            .with(csrf())
        )
        .andExpect(status().isOk())
        .andExpect(view().name("urlaubsterminAnmeldung"));
    verify(studentService, times(0)).urlaubsterminAnmelden(28324332L,
        "christianmeter",
        LocalDate.of(2022, 3, 23),
        LocalTime.of(9, 30),
        LocalTime.of(8, 30));
  }

  @Test
  @DisplayName("Ein Urlaubstermin ist stornierbar und der Service wird aufgerufen")
  void test_4() throws Exception {
    MockHttpSession session = studentSession();

    mvc.perform(post("/urlaubsterminstornieren")
            .session(session)
            .param("datum", "2022-03-23")
            .param("von", "09:30")
            .param("bis", "10:30")
            .with(csrf())
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"));
    verify(studentService).urlaubsterminStornieren(28324332L,
        LocalDate.of(2022, 3, 23),
        LocalTime.of(9, 30),
        LocalTime.of(10, 30),
        LocalDate.now());
  }
}
