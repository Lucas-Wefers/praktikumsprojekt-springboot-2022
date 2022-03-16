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

  public void fuegeKlausurHinzu(Long klausurReferenz, LocalDate datum,
                                LocalTime vonKlausurFreistellung,
                                LocalTime bisKlausurFreistellung) {
    if (klausurReferenzen.stream()
        .anyMatch(x -> x.id().equals(klausurReferenz))) {
      return;
    }

    klausurReferenzen.add(new KlausurReferenz(klausurReferenz));

    List<Urlaubstermin> urlaubstermineMitSelbemDatum = urlaubstermine.stream()
        .filter(x -> x.getDatum().equals(datum))
        .toList();

    urlaubstermine.removeAll(urlaubstermineMitSelbemDatum);

    LocalTime vonUrlaub;
    LocalTime bisUrlaub;
    for (Urlaubstermin urlaubstermin : urlaubstermineMitSelbemDatum) {
      vonUrlaub = urlaubstermin.getVon();
      bisUrlaub = urlaubstermin.getBis();

      if (vonUrlaub.isBefore(vonKlausurFreistellung) && bisUrlaub.isBefore(
          bisKlausurFreistellung)) {
        // behalte urlaub vor vonKlausurFreistellung verwerfe den Rest (abspalten des hinteren Teils)
        // Urlaub 10:00 - 11:00, Klausur 10:45 - 11:45
        urlaubstermine.add(new Urlaubstermin(datum, vonUrlaub, vonKlausurFreistellung));

      } else if (vonKlausurFreistellung.isBefore(vonUrlaub) && bisKlausurFreistellung.isBefore(
          bisUrlaub)) {
        // behalte urlaub nach vonKlausurFreistellung verwerfe den Rest (abspalten des vorderen Teils)
        // Urlaub 10:00 - 11:00, Klausur 9:45 - 10:45
        urlaubstermine.add(new Urlaubstermin(datum, bisKlausurFreistellung, bisUrlaub));

      } else if (vonUrlaub.isBefore(vonKlausurFreistellung) &&
          bisUrlaub.isAfter(bisKlausurFreistellung)) {
        urlaubstermine.add(new Urlaubstermin(datum, vonUrlaub, vonKlausurFreistellung));
        urlaubstermine.add(new Urlaubstermin(datum, bisKlausurFreistellung, bisUrlaub));

      } else if (bisKlausurFreistellung.isBefore(vonUrlaub)
          || bisUrlaub.isBefore(vonKlausurFreistellung)) {
        urlaubstermine.add(urlaubstermin);
      }
    }
  }

  public void fuegeUrlaubsterminHinzu(LocalDate datum, LocalTime von, LocalTime bis,
                                      boolean istKlausurtag) {
    Urlaubstermin urlaubstermin = new Urlaubstermin(datum, von, bis);
    if (istKlausurtag) {
      urlaubstermine.add(urlaubstermin);
    } else if (istValideUrlaubsdauer(urlaubstermin)) {
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
    return urlaubstermin.dauer().toMinutes() <= 150
        || urlaubstermin.dauer().toMinutes() == 240;
  }

  public List<Urlaubstermin> getUrlaubstermine() {
    return List.copyOf(urlaubstermine);
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
