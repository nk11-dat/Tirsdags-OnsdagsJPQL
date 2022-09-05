package facades;

import entities.Semester;
import entities.Student;
import entities.Teacher;

import java.util.List;
import java.util.Set;

public interface DataFacade {
    List<Student> getAllStudents();
    Student createStudent(Student student);
    Semester createSemester(Semester semester);
    Teacher createTeacher(Teacher teacher);
    Semester addSutdentToSemester(Student student, Semester semester);
    Semester addTeacherToSemester(Teacher teacher, Semester semester);
    Semester removeTeacherFromSemester(Teacher teacher, Semester semester);
    Semester updateSemester(Semester semester, String name, String description);
    Set<Student> getAllStudentsBySemester(Semester semester);
    Set<Student> getAllStudentsByTeacher(Teacher teacher);
}
