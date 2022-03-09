package de.hhu.chicken;

import de.hhu.chicken.service.repositories.KlausurRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class ChickenApplicationTests {

  @MockBean
  KlausurRepository klausurRepository;

  @Test
  void contextLoads() {
  }

}
