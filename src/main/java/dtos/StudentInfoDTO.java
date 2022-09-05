package dtos;

import entities.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentInfoDTO {
    private String fullName;
    private long studentId;
    private String classNameThisSemester;
    private String classDescription;
    private List<StudentInfoDTO> all = new ArrayList<>();

    //    public StudentInfoDTO(String firstName, String lastName, long studentId, String classNameThisSemester, String classDescription) {
//        this.fullName = firstName + " " + lastName;
//        this.studentId = studentId;
//        this.classNameThisSemester = classNameThisSemester;
//        this.classDescription = classDescription;
//    }
//
    public StudentInfoDTO(Student student) {
        this.fullName = student.getFirstname() + " " + student.getLastname();
        this.studentId = student.getId();
        this.classNameThisSemester = student.getSemester().getName();
        this.classDescription = student.getSemester().getDescription();
    }

    public StudentInfoDTO(List<Student> studentEntities) {
        studentEntities.forEach((student) -> {
            all.add(new StudentInfoDTO(student));
        });
    }

    public List<StudentInfoDTO> getAll() {
        return all;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getClassNameThisSemester() {
        return classNameThisSemester;
    }

    public void setClassNameThisSemester(String classNameThisSemester) {
        this.classNameThisSemester = classNameThisSemester;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }

    @Override
    public String toString() {
        return "StudentInfoDTO{" +
                "fullName='" + fullName + '\'' +
                ", studentId=" + studentId +
                ", classNameThisSemester='" + classNameThisSemester + '\'' +
                ", classDescription='" + classDescription + '\'' +
                '}';
    }
}
