package de.hhu.chicken.domain.student;

import de.hhu.chicken.domain.stereotypes.AggregateRoot;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AggregateRoot
public class Student {

  GithubHandle githubHandle;
  List<KlausurReferenz> klausurReferenzen = new ArrayList<>();
  List<Urlaubstermin> urlaubstermine = new ArrayList<>();

  public Student(String githubHandle) {
    this.githubHandle = new GithubHandle(githubHandle);
  }

  public void fuegeKlausurterminHinzu(Long klausurReferenz) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  public void fuegeUrlaubsterminHinzu(LocalDate datum, LocalTime von, LocalTime bis,
                                      boolean istKlausurtag) {
    Urlaubstermin urlaubstermin = new Urlaubstermin(datum, von, bis);
    if (istKlausurtag) {
      urlaubstermine.add(urlaubstermin);
    }
    else if (istValideUrlaubsdauer(urlaubstermin)) {
      urlaubstermine.add(urlaubstermin);
    }
  }

  public double berechneResturlaub() {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  public String getGithubHandle() {
    return githubHandle.handle();
  }

  public List<Long> getKlausurReferenzen() {
    return klausurReferenzen.stream()
        .map(KlausurReferenz::id)
        .collect(Collectors.toList());
  }

  private boolean istValideUrlaubsdauer(Urlaubstermin urlaubstermin) {
    List<Urlaubstermin> urlaubstermineMitGleichemDatum =
        urlaubstermine.stream()
            .filter(x -> x.getDatum().equals(urlaubstermin.getDatum()))
            .collect(Collectors.toList());

    if (urlaubstermineMitGleichemDatum.size() == 1) {
      Urlaubstermin urlaubstermin2 = urlaubstermineMitGleichemDatum.get(0);
      return istValideUrlaubsdauerFuerZweiUrlaube(urlaubstermin, urlaubstermin2);
    }

    if (urlaubstermineMitGleichemDatum.size() == 0) {
      return istValideUrlaubsdauerFuerEinenUrlaub(urlaubstermin);
    }

    return false;
  }

  private boolean istValideUrlaubsdauerFuerZweiUrlaube(Urlaubstermin urlaubstermin,
                                                       Urlaubstermin urlaubstermin2) {
    boolean istValideUrlaubsDauerFuerZweiUrlaube =
        urlaubstermin.dauer().plus(urlaubstermin2.dauer()).toMinutes() <= 150;

    if (urlaubstermin.getVon().equals(LocalTime.of(9, 30))
        && urlaubstermin2.getBis().equals(LocalTime.of(13, 30))) {
      return istValideUrlaubsDauerFuerZweiUrlaube;
    }
    if (urlaubstermin2.getVon().equals(LocalTime.of(9, 30))
        && urlaubstermin.getBis().equals(LocalTime.of(13, 30))) {
      return istValideUrlaubsDauerFuerZweiUrlaube;
    }
    return false;
  }

  private boolean istValideUrlaubsdauerFuerEinenUrlaub(Urlaubstermin urlaubstermin) {
    if (urlaubstermin.dauer().toMinutes() <= 150
        || urlaubstermin.dauer().toMinutes() == 240) {
      return true;
    }
    return false;
  }

  public List<Urlaubstermin> getUrlaubstermine() {
    return urlaubstermine;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Student student = (Student) o;
    return githubHandle.equals(student.githubHandle);
  }

  @Override
  public int hashCode() {
    return Objects.hash(githubHandle);
  }
}
