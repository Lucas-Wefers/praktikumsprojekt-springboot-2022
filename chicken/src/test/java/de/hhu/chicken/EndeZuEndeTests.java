package de.hhu.chicken;

import static de.hhu.chicken.infrastructure.web.configuration.AuthenticationTemplates.studentSession;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import de.hhu.chicken.service.logger.UrlaubsterminLogger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@Sql({"classpath:db/migration/V1__init.sql",
    "classpath:db/migration/V2__create_student_tables.sql"})
public class EndeZuEndeTests {

  @MockBean
  private UrlaubsterminLogger logger;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Bei der Anmeldung eines validen Urlaubs ist dieser auch auf der Uebersichtsseite "
      + "zu sehen")
  void test_1() throws Exception {
    MockHttpSession session = studentSession();

    mockMvc.perform(post("/urlaubsterminanmeldung")
        .session(session)
        .with(csrf())
        .param("datum", "2022-03-30")
        .param("von", "09:30")
        .param("bis", "10:30"));

    String html = mockMvc.perform(get("/")
            .session(session))
        .andReturn()
        .getResponse()
        .getContentAsString();

    assertThat(html).contains("2022-03-30", "09:30", "10:30");
  }

  @Test
  @DisplayName("Beim Eintragen und Anmelden einer Klausur ist diese auch auf der Uebersichtsseite"
      + " zu sehen")
  void test_2() throws Exception {
    MockHttpSession session = studentSession();

    mockMvc.perform(post("/klausuren")
        .session(session)
        .with(csrf())
        .param("fach", "Programmierung")
        .param("veranstaltungsId", "1234")
        .param("isPraesenz", "true")
        .param("datum", "2022-03-30")
        .param("von", "09:30")
        .param("bis", "11:30"));

    mockMvc.perform(post("/klausuranmeldung")
        .session(session)
        .with(csrf())
        .param("id", "1"));

    String html = mockMvc.perform(get("/")
            .session(session))
        .andReturn()
        .getResponse()
        .getContentAsString();

    assertThat(html)
        .contains("Präsenzklausur", "Programmierung", "2022-03-30", "09:30", "11:30", "13:30");
  }

  @Test
  @DisplayName("Beim Eintragen und Anmelden einer Klausur und einem Urlaub, wird der "
      + "Urlaubstermin angepasst und die zwei Urlaube und die Klausur in der Uebersicht angezeigt")
  void test_3() throws Exception {
    MockHttpSession session = studentSession();

    mockMvc.perform(post("/klausuren")
        .session(session)
        .with(csrf())
        .param("fach", "Analysis")
        .param("veranstaltungsId", "12345")
        .param("isPraesenz", "false")
        .param("datum", "2022-03-30")
        .param("von", "10:30")
        .param("bis", "11:30"));

    mockMvc.perform(post("/klausuranmeldung")
        .session(session)
        .with(csrf())
        .param("id", "1"));

    mockMvc.perform(post("/urlaubsterminanmeldung")
        .session(session)
        .with(csrf())
        .param("datum", "2022-03-30")
        .param("von", "09:30")
        .param("bis", "13:30"));

    String html = mockMvc.perform(get("/")
            .session(session))
        .andReturn()
        .getResponse()
        .getContentAsString();

    assertThat(html).contains("Onlineklausur", "Analysis", "2022-03-30", "10:30", "11:30");
    assertThat(html).contains("<td>2022-03-30</td>", "<td>09:30</td>", "<td>10:00</td>");
    assertThat(html).contains("<td>2022-03-30</td>", "<td>11:30</td>", "<td>13:30</td>");
  }
}
