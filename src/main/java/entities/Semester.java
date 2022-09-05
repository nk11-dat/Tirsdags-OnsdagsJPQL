package entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    private String description;
    private String name;
    @OneToMany(mappedBy = "semester")
    private Set<Student> students = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name="TEACHER_SEMESTER",
            joinColumns=@JoinColumn(name="teaching_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="teachers_ID", referencedColumnName="ID"))
    Set<Teacher> teachers = new HashSet<>();

    public Semester() {
    }

    public Semester(String description, String name) {
        this.description = description;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void addStudents(Student student) {
        this.students.add(student);
        student.setSemester(this);
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
        teacher.getSemesters().add(this);
    }

    public void removeTeacher(Teacher teacher) {
        this.teachers.remove(teacher);
        teacher.getSemesters().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Semester semester = (Semester) o;

        if (id != semester.id) return false;
        if (description != null ? !description.equals(semester.description) : semester.description != null)
            return false;
        if (name != null ? !name.equals(semester.name) : semester.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Semester{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
