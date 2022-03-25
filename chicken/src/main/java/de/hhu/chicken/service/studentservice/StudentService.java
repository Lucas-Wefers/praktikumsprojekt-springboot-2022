package de.hhu.chicken.service.studentservice;

import de.hhu.chicken.configuration.PraktikumsUhrzeitConfiguration;
import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.domain.student.Student;
import de.hhu.chicken.service.logger.UrlaubsterminLogger;
import de.hhu.chicken.service.repositories.KlausurRepository;
import de.hhu.chicken.service.repositories.StudentRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

  private final StudentRepository studentRepository;
  private final KlausurRepository klausurRepository;
  private final UrlaubsterminLogger urlaubsterminLogger;
  private final PraktikumsUhrzeitConfiguration uhrzeitConfiguration;

  public StudentService(StudentRepository studentRepository,
                        KlausurRepository klausurRepository,
                        UrlaubsterminLogger urlaubsterminLogger,
                        PraktikumsUhrzeitConfiguration uhrzeitConfiguration) {
    this.studentRepository = studentRepository;
    this.klausurRepository = klausurRepository;
    this.urlaubsterminLogger = urlaubsterminLogger;
    this.uhrzeitConfiguration = uhrzeitConfiguration;
  }

  public void klausurAnmelden(Long githubId, String handle, Long klausurId) {
    Student student = studentRepository.findStudentByGithubId(githubId);
    if (student == null) {
      student = new Student(githubId, handle);
    }
    Klausur klausur = klausurRepository.findKlausurById(klausurId);
    student.fuegeKlausurHinzu(klausurId,
        klausur.getDatum(),
        klausur.berechneFreistellungsStartzeitpunkt(
            uhrzeitConfiguration.getPraktikumsStartuhrzeit()),
        klausur.berechneFreistellungsEndzeitpunkt(
            uhrzeitConfiguration.getPraktikumsEnduhrzeit()));
    studentRepository.studentSpeichern(student);
  }

  public void urlaubsterminAnmelden(Long githubId, String handle, LocalDate datum, LocalTime von,
                                    LocalTime bis) {
    Student student = studentRepository.findStudentByGithubId(githubId);
    if (student == null) {
      student = new Student(githubId, handle);
    }
    List<Long> klausurReferenzen = student.getKlausurReferenzen();
    List<Klausur> klausurenAmGleichenTag = klausurReferenzen.stream()
        .map(klausurRepository::findKlausurById)
        .filter(x -> x.getDatum().equals(datum))
        .toList();

    boolean istKlausurTag = !klausurenAmGleichenTag.isEmpty();
    List<Klausur> ueberschneideneKlausuren = klausurenAmGleichenTag.stream()
        .filter(x -> von.isBefore(x.berechneFreistellungsEndzeitpunkt(
            uhrzeitConfiguration.getPraktikumsEnduhrzeit())))
        .filter(x -> x.berechneFreistellungsStartzeitpunkt(
            uhrzeitConfiguration.getPraktikumsStartuhrzeit()).isBefore(bis))
        .toList();

    for (Klausur klausur : ueberschneideneKlausuren) {
      student.storniereKlausur(klausur.getId());
    }
    boolean urlaubWurdeHinzugefuegt =
        student.fuegeUrlaubsterminHinzu(datum, von, bis, istKlausurTag,
            uhrzeitConfiguration.getPraktikumsStartuhrzeit(),
            uhrzeitConfiguration.getPraktikumsEnduhrzeit());
    for (Klausur klausur : ueberschneideneKlausuren) {
      student.fuegeKlausurHinzu(klausur.getId(),
          klausur.getDatum(),
          klausur.berechneFreistellungsStartzeitpunkt(
              uhrzeitConfiguration.getPraktikumsStartuhrzeit()),
          klausur.berechneFreistellungsEndzeitpunkt(
              uhrzeitConfiguration.getPraktikumsEnduhrzeit()));
    }
    if (urlaubWurdeHinzugefuegt) {
      studentRepository.studentSpeichern(student);
      String logNachricht = "Der Student " + handle + " hat für den " + datum
          + " einen Urlaub von " + von + " bis " + bis + " gebucht.";
      urlaubsterminLogger.eintragen(logNachricht);
    }
  }

  public void klausurStornieren(Long githubId, Long klausurId, LocalDate heute) {
    Student student = studentRepository.findStudentByGithubId(githubId);
    Klausur klausurById = klausurRepository.findKlausurById(klausurId);
    if (klausurById.isStornierbar(heute)) {
      student.storniereKlausur(klausurId);
      studentRepository.studentSpeichern(student);
    }
  }

  public void urlaubsterminStornieren(Long githubId, LocalDate datum, LocalTime von,
                                      LocalTime bis, LocalDate heute) {
    Student student = studentRepository.findStudentByGithubId(githubId);
    if (student.isUrlaubsterminStornierbar(datum, heute)) {
      student.storniereUrlaub(datum, von, bis);
      String logNachricht = "Der Student " + student.getHandle() + " hat den Urlaub am " + datum
          + " von " + von + " bis " + bis + " storniert.";
      urlaubsterminLogger.eintragen(logNachricht);
      studentRepository.studentSpeichern(student);
    }
  }

  public Student findStudentByGithubId(Long githubId) {
    return studentRepository.findStudentByGithubId(githubId);
  }

  public List<Klausur> alleAngemeldetenKlausuren(Long githubId) {
    Student student = studentRepository.findStudentByGithubId(githubId);
    if (student == null) {
      return List.of();
    }
    List<Long> klausurReferenzen = student.getKlausurReferenzen();
    return klausurReferenzen.stream()
        .map(klausurRepository::findKlausurById)
        .toList();
  }
}
