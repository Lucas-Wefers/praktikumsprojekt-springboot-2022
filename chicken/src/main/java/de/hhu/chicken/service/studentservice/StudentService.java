package de.hhu.chicken.service.studentservice;

import de.hhu.chicken.domain.klausur.Klausur;
import de.hhu.chicken.domain.student.Student;
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

  public StudentService(StudentRepository studentRepository,
                        KlausurRepository klausurRepository) {
    this.studentRepository = studentRepository;
    this.klausurRepository = klausurRepository;
  }

  public void klausurAnmelden(Long githubId, String handle, Long klausurId) {
    Student student = studentRepository.findStudentByGithubId(githubId);
    if (student == null) {
      student = new Student(githubId, handle);
    }
    Klausur klausur = klausurRepository.findKlausurById(klausurId);
    student.fuegeKlausurHinzu(klausurId,
        klausur.getDatum(),
        klausur.berechneFreistellungsStartzeitpunkt(),
        klausur.berechneFreistellungsEndzeitpunkt());
    studentRepository.studentSpeichern(student);
  }

  public void urlaubsterminAnmelden(Long githubId, String handle, LocalDate datum, LocalTime von, LocalTime bis) {
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
        .filter(x -> von.isBefore(x.berechneFreistellungsEndzeitpunkt()))
        .filter(x -> x.berechneFreistellungsStartzeitpunkt().isBefore(bis))
        .toList();

    for (Klausur klausur : ueberschneideneKlausuren) {
      student.storniereKlausur(klausur.getId());
    }
    student.fuegeUrlaubsterminHinzu(datum, von, bis, istKlausurTag);
    for (Klausur klausur : ueberschneideneKlausuren) {
      student.fuegeKlausurHinzu(klausur.getId(),
          klausur.getDatum(),
          klausur.berechneFreistellungsStartzeitpunkt(),
          klausur.berechneFreistellungsEndzeitpunkt());
    }
    studentRepository.studentSpeichern(student);
  }

  public void klausurStornieren(Long githubId, Long klausurId) {
    Student student = studentRepository.findStudentByGithubId(githubId);
    student.storniereKlausur(klausurId);
    studentRepository.studentSpeichern(student);
  }

  public void urlaubsterminStornieren(Long githubId, LocalDate datum, LocalTime von,
                                      LocalTime bis) {
    Student student = studentRepository.findStudentByGithubId(githubId);
    student.storniereUrlaub(datum, von, bis);
    studentRepository.studentSpeichern(student);
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
