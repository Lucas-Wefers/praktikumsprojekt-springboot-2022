package de.hhu.chicken.service.repositories;

import de.hhu.chicken.domain.student.Student;

public interface StudentRepository {

  Student findStudentByGithubId(Long githubId);

  void studentSpeichern(Student student);
}
