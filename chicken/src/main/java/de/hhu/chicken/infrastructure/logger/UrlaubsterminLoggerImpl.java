package de.hhu.chicken.infrastructure.logger;

import de.hhu.chicken.service.logger.UrlaubsterminLogger;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class UrlaubsterminLoggerImpl implements UrlaubsterminLogger {

  private final File logFile;

  public UrlaubsterminLoggerImpl() {
    logFile = new File("urlaubslog.txt");
  }

  @Override
  public void eintragen(String logNachricht) {
    try {
      FileOutputStream fileStream = new FileOutputStream(logFile, true);
      Writer writer = new OutputStreamWriter(fileStream, StandardCharsets.UTF_8);
      BufferedWriter bufferedWriter = new BufferedWriter(writer);
      LocalDateTime currentTime = LocalDateTime.now();
      DateTimeFormatter uhrzeitFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
      String logDatum = currentTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
      String logZeit = currentTime.format(uhrzeitFormat);
      bufferedWriter.write("[" + logDatum + "] [" + logZeit + "] " + logNachricht);
      bufferedWriter.newLine();
      bufferedWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
