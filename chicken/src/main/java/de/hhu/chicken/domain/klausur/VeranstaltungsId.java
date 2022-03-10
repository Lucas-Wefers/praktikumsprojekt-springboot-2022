package de.hhu.chicken.domain.klausur;

public class VeranstaltungsId {

  Long id;

  public VeranstaltungsId(Long veranstaltungsId) {
    id = veranstaltungsId;
  }

  @Override
  public String toString() {
    return "VeranstaltungsId{" +
        "id=" + id +
        '}';
  }
}
