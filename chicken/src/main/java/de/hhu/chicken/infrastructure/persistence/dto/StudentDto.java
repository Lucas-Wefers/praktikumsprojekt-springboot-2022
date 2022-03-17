package de.hhu.chicken.infrastructure.persistence.dto;

import de.hhu.chicken.domain.student.Urlaubstermin;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table("student")
public class StudentDto implements Persistable<String> {

  @Id
  private final String handle;

  private final List<Long> klausurReferenzen;
  private final List<Urlaubstermin> urlaubstermine;

  public StudentDto(String handle, List<Long> klausurReferenzen,
      List<Urlaubstermin> urlaubstermine) {
    this.handle = handle;
    this.klausurReferenzen = List.copyOf(klausurReferenzen);
    this.urlaubstermine = List.copyOf(urlaubstermine);
  }

  public String getHandle() {
    return handle;
  }

  public List<Long> getKlausurReferenzen() {
    return klausurReferenzen;
  }

  public List<Urlaubstermin> getUrlaubstermine() {
    return urlaubstermine;
  }

  @Override
  public String getId() {
    return handle;
  }

  @Override
  public boolean isNew() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StudentDto that = (StudentDto) o;
    return handle.equals(that.handle);
  }

  @Override
  public int hashCode() {
    return Objects.hash(handle);
  }
}
