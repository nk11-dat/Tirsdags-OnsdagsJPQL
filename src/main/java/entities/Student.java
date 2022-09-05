package entities;

import javax.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    private String firstname;
    private String lastname;
//    @ForeignKey(name = "FK_STUDENT_CURRENTSEMESTER_ID")
//    private Long currentsemesterId;
    @ManyToOne
    @JoinColumn(name = "CURRENTSEMESTER_ID")
    private Semester semester;

    public Student() {
    }

    public Student(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "FIRSTNAME")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "LASTNAME")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (id != student.id) return false;
        if (firstname != null ? !firstname.equals(student.firstname) : student.firstname != null) return false;
        if (lastname != null ? !lastname.equals(student.lastname) : student.lastname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }

//    @Override
//    public int compareTo(Object o) {
//        Student s = ((Student) o);
//        int semesterId1 = (int) this.getSemester().getId();
//        int semesterId2 = (int) s.getSemester().getId();
//        if(semesterId1>semesterId2){
//            return 1;
//        }else if(semesterId1<semesterId2){
//            return -1;
//        }else{
//            return 0;
//        }
//    }
}
