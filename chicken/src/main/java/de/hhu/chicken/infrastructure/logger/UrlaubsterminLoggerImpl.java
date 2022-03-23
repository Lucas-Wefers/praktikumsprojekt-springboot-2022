package de.hhu.chicken.infrastructure.logger;

import de.hhu.chicken.service.logger.UrlaubsterminLogger;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
  }

  @Override
  public void eintragen(String logNachricht) {
    try {
      FileOutputStream fileStream = new FileOutputStream(logFile);
      Writer writer = new OutputStreamWriter(fileStream, "UTF-8");
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
