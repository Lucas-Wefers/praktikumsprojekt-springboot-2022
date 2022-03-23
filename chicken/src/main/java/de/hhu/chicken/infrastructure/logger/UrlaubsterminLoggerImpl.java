package de.hhu.chicken.infrastructure.logger;

import de.hhu.chicken.service.logger.UrlaubsterminLogger;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import org.springframework.stereotype.Component;

@Component
public class UrlaubsterminLoggerImpl implements UrlaubsterminLogger {

  private final File logFile;

  public UrlaubsterminLoggerImpl() throws IOException {
    logFile = new File("urlaubslog.txt");
    logFile.createNewFile();
  }

  @Override
  public void eintragen(String logNachricht) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
      LocalDateTime currentTime = LocalDateTime.now();
      DateTimeFormatter uhrzeitFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
      String logDatum = currentTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
      String logZeit = currentTime.format(uhrzeitFormat);
      writer.write("[" + logDatum + "] [" + logZeit + "] " + logNachricht);
      writer.newLine();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
