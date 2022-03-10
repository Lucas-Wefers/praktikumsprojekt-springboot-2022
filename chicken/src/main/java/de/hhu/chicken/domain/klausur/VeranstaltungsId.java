package de.hhu.chicken.domain.klausur;

public class VeranstaltungsId {

  Long id;

  public VeranstaltungsId(Long veranstaltungsId) {
    id = veranstaltungsId;
  }

  public Long getId() {
    return id;
  }

  @Override
  public String toString() {
    return "VeranstaltungsId{" +
        "id=" + id +
        '}';
  }
}
