package de.hhu.chicken.infrastructure.web.controllers.student;

import static de.hhu.chicken.infrastructure.web.configuration.AuthenticationTemplates.studentSession;
import static de.hhu.chicken.templates.KlausurTemplates.zweiBeispielklausuren;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import de.hhu.chicken.configuration.PraktikumsUhrzeitConfiguration;
import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.domain.student.Student;
import de.hhu.chicken.service.klausurservice.KlausurService;
import de.hhu.chicken.service.studentservice.StudentService;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class UebersichtControllerTest {

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
    when(uhrzeitConfiguration.getPraktikumsStartuhrzeit())
        .thenReturn(LocalTime.of(9, 30));
    when(uhrzeitConfiguration.getPraktikumsEnduhrzeit())
        .thenReturn(LocalTime.of(13, 30));
    MockHttpSession session = studentSession();
    Student student = new Student(28324332L, "christianmeter");
    List<Klausur> klausurenVomStudenten = zweiBeispielklausuren();
    when(studentService.findStudentByGithubId(28324332L))
        .thenReturn(student);
    when(studentService.alleAngemeldetenKlausuren(28324332L))
        .thenReturn(klausurenVomStudenten);

    mvc.perform(get("/")
            .session(session))
        .andExpect(status().isOk())
        .andExpect(model().attribute("student", student))
        .andExpect(model().attribute("klausuren", klausurenVomStudenten))
        .andExpect(view().name("uebersicht"));
  }
}