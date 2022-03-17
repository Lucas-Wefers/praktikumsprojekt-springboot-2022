package de.hhu.chicken.service.repositories;

import de.hhu.chicken.domain.student.Student;

public interface StudentRepository {

  Student findStudentByHandle(String handle);

  Student studentSpeichern(Student student);
}
