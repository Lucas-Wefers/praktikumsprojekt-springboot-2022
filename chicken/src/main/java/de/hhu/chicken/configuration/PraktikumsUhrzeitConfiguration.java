package de.hhu.chicken.configuration;

import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PraktikumsUhrzeitConfiguration {

  public static LocalTime praktikumsStartuhrzeit;
  public static LocalTime praktikumsEnduhrzeit;

  @Value("${praktikumszeiten.start.uhrzeit}")
  public static void setPraktikumsStartuhrzeit(String praktikumsStartuhrzeit) {
    PraktikumsUhrzeitConfiguration.praktikumsStartuhrzeit = LocalTime.parse(praktikumsStartuhrzeit);
  }

  @Value("${praktikumszeiten.ende.uhrzeit}")
  public static void setPraktikumsEnduhrzeit(String praktikumsEnduhrzeit) {
    PraktikumsUhrzeitConfiguration.praktikumsEnduhrzeit = LocalTime.parse(praktikumsEnduhrzeit);
  }
}
