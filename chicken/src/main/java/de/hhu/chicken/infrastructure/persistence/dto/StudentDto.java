package de.hhu.chicken.infrastructure.persistence.dto;

import de.hhu.chicken.domain.student.Urlaubstermin;
import java.util.ArrayList;
import java.util.List;
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

  @Override
  public String getId() {
    return handle;
  }

  @Override
  public boolean isNew() {
    return true;
  }
}
