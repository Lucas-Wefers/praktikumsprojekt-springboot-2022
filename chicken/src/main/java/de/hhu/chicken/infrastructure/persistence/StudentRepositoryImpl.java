package de.hhu.chicken.infrastructure.persistence;

import de.hhu.chicken.domain.student.Student;
import de.hhu.chicken.infrastructure.persistence.dao.StudentDao;
import de.hhu.chicken.infrastructure.persistence.dto.KlausurReferenzDto;
import de.hhu.chicken.infrastructure.persistence.dto.StudentDto;
import de.hhu.chicken.service.repositories.StudentRepository;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

  private final StudentDao studentDao;

  public StudentRepositoryImpl(StudentDao studentDao) {
    this.studentDao = studentDao;
  }

  private Student studentDtoToStudent(StudentDto studentDto) {
    return new Student(studentDto.getGithubId(), studentDto.getHandle(),
        studentDto.getKlausurReferenzen().stream()
            .map(KlausurReferenzDto::klausurId)
            .toList(),
        studentDto.getUrlaubstermine());
  }

  private StudentDto studentToStudentDto(Student student) {
    return new StudentDto(student.getGithubId(), student.getHandle(),
        student.getKlausurReferenzen().stream()
            .map(KlausurReferenzDto::new)
            .toList(),
        student.getUrlaubstermine());
  }

  private StudentDto updateStudentToStudentDto(Student student) {
    return new StudentDto(student.getGithubId(), false,
        student.getHandle(),
        student.getKlausurReferenzen().stream()
            .map(KlausurReferenzDto::new)
            .toList(),
        student.getUrlaubstermine());
  }

  @Override
  public void studentSpeichern(Student student) {
    if (studentDao.existsById(student.getGithubId())) {
      studentDao.save(updateStudentToStudentDto(student));
      return;
    }
    studentDao.save(studentToStudentDto(student));
  }

  @Override
  public Student findStudentByGithubId(Long githubId) {
    StudentDto studentDto = studentDao.findById(githubId).orElse(null);
    if (studentDto == null) {
      return null;
    }

    return studentDtoToStudent(studentDto);
  }
}
