package de.hhu.chicken.infrastructure.persistence;

import de.hhu.chicken.domain.student.Student;
import de.hhu.chicken.infrastructure.persistence.dao.StudentDao;
import de.hhu.chicken.infrastructure.persistence.dto.StudentDto;
import de.hhu.chicken.service.repositories.StudentRepository;

public class StudentRepositoryImpl implements StudentRepository {

  private final StudentDao studentDao;

  public StudentRepositoryImpl(StudentDao studentDao) {
    this.studentDao = studentDao;
  }

  private Student studentDtoToStudent(StudentDto studentDto) {
    return new Student(studentDto.getHandle(),
        studentDto.getKlausurReferenzen(),
        studentDto.getUrlaubstermine());
  }

  private StudentDto studentToStudentDto(Student student) {
    return new StudentDto(student.getHandle(),
        student.getKlausurReferenzen(),
        student.getUrlaubstermine());
  }

  private StudentDto updateStudentToStudentDto(Student student) {
    return new StudentDto(false,
        student.getHandle(),
        student.getKlausurReferenzen(),
        student.getUrlaubstermine());
  }

  @Override
  public void studentSpeichern(Student student) {
    if (studentDao.existsById(student.getHandle())) {
      studentDao.save(updateStudentToStudentDto(student));
      return;
    }
    studentDao.save(studentToStudentDto(student));
  }

  @Override
  public Student findStudentByHandle(String handle) {
    StudentDto studentDto = studentDao.findById(handle).orElse(null);
    if (studentDto == null) {
      return null;
    }

    return studentDtoToStudent(studentDto);
  }
}
