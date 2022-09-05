package facades;

import dtos.StudentInfoDTO;
import entities.Semester;
import entities.Student;
import entities.Teacher;

import javax.persistence.*;
import java.util.*;


public class SchoolFacade implements DataFacade {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void main(String[] args) {
        SchoolFacade sf = new SchoolFacade();

        System.out.println(ANSI_GREEN + "*** 1)Show all students ***" + ANSI_RESET);
        sf.getAllStudents().forEach(System.out::println);
        System.out.println();

        System.out.println(ANSI_GREEN + "*** 2)Create a new student ***" + ANSI_RESET);
        Student student = sf.createStudent(new Student("Luke", "Skywalker"));
        Student student2 = sf.createStudent(new Student("Obi-wan", "Kenobi"));
        System.out.println(student);
        System.out.println();

        System.out.println(ANSI_GREEN + "*** 3)Create a new semester ***" + ANSI_RESET);
        Semester semester = sf.createSemester(new Semester("Jedi training", "Lightside101"));
        Semester semester2 = sf.createSemester(new Semester("Sith training", "Darkside66"));
        System.out.println(semester);
        System.out.println();


        System.out.println(ANSI_GREEN + "*** 4)Create a new teacher ***" + ANSI_RESET);
        Teacher teacher = sf.createTeacher(new Teacher("Anikin", "Skywalker"));
        System.out.println(teacher);
        System.out.println();

        System.out.println(ANSI_GREEN + "*** 5)Add a student to a semester ***" + ANSI_RESET);
        System.out.println(sf.addSutdentToSemester(student, semester));
        System.out.println(sf.addSutdentToSemester(student2, semester));
        sf.updateSemester(semester);
        System.out.println();

        System.out.println(ANSI_GREEN + "*** 6)Add a teacher to a semester ***" + ANSI_RESET);
        System.out.println(sf.addTeacherToSemester(teacher, semester));
        System.out.println(sf.addTeacherToSemester(teacher, semester2));
        sf.updateSemester(semester);
        sf.updateSemester(semester2);
        System.out.println();

        System.out.println(ANSI_GREEN + "*** 7)Remove a teacher from a semester ***" + ANSI_RESET);
        sf.removeTeacherFromSemester(teacher, semester2);
        sf.updateSemester(semester2);
        System.out.println("removed: " + teacher + "\tfrom: " + semester2);
        System.out.println();

        System.out.println(ANSI_GREEN + "*** 8)Update a semesters name and description ***" + ANSI_RESET);
        System.out.println(sf.updateSemester(semester, "Not jedi training", "Drinking blue milk"));
        System.out.println();

        System.out.println(ANSI_GREEN + "*** 9)Get all students from a semester ***" + ANSI_RESET);
        sf.getAllStudentsBySemester(3).forEach(System.out::println);
        System.out.println();

        System.out.println(ANSI_GREEN + "*** 10)Get all Students by a specific teacher ***" + ANSI_RESET);
        sf.getAllStudentsByTeacher(1).forEach(System.out::println);
        System.out.println();

        System.out.println(ANSI_GREEN + "*** 11)Find all Students in the System with the first name Anders ***" + ANSI_RESET);
        sf.getAllStudentsByFirstName("Anders").forEach(System.out::println);
        System.out.println();

        System.out.println(ANSI_GREEN + "*** 12)Find (using JPQL) all Students in the system with the last name And ***" + ANSI_RESET);
        sf.getAllStudentsByLastName("And").forEach(System.out::println);
        System.out.println();

        System.out.println(ANSI_GREEN + "*** 13)Find (using JPQL)  the total number of students, for a semester given the semester name as a parameter. ***" + ANSI_RESET);
        String semesterName = "CLdat-b14e";
        System.out.println(semesterName + " student count: " + sf.getStudentCountBySemesterName(semesterName));
        System.out.println();

        System.out.println(ANSI_GREEN + "*** 14)Find (using JPQL) the total number of students that has a particular teacher. ***" + ANSI_RESET);
//        String teacherFirstName = "CLdat-b14e", teacherLastName;
        Long dingdong = 1L;
        Teacher chosenTeacher = sf.getTeacherById(dingdong); //Skift lære her
        System.out.println(chosenTeacher.getFirstname() + " " + chosenTeacher.getLastname() + " student count: " + sf.getStudentCountByTeacher(chosenTeacher));
        System.out.println();

        System.out.println(ANSI_GREEN + "*** 15)Find (using JPQL) the teacher who teaches the most semesters. ***" + ANSI_RESET);
        Teacher maxTeacher = sf.getTeacherWhoTeachesMostSemesters();
        System.out.println(maxTeacher.getFirstname() + " " + maxTeacher.getLastname() + " antal semestre: " + maxTeacher.getSemesters().size());
        System.out.println();

        System.out.println(ANSI_GREEN + "*** 16)Find the semester that has the fewest students ***" + ANSI_RESET);
        Semester fewestStudents = sf.getSemesterWithFewestStudents();
        System.out.println(fewestStudents.getName() + " had fewest students with: " + fewestStudents.getStudents().size());
        System.out.println();

        System.out.println(ANSI_GREEN + "*** 17)StudentInfoDTO List ***" + ANSI_RESET);
        sf.getStudentsInfo().forEach(System.out::println);
        System.out.println();

        System.out.println(ANSI_GREEN + "*** 18)StudentInfoDTO entity by ID ***" + ANSI_RESET);
        long id = 4;
        System.out.println("ID used for search: " + id + " result:\n" + sf.getStudentInfoById(id));
        System.out.println();
    }

    private StudentInfoDTO getStudentInfoById(long id) {
        EntityManager em = getEntityManager();
        StudentInfoDTO studentInfo;

        try {
            //String firstName, String lastName, long studentId, String classNameThisSemester, String classDescription
            TypedQuery<Student> query = em.createQuery(
                    "select stu from Student stu where stu.id = :id",
                    Student.class);
            query.setParameter("id", id);
            Student student = query.getSingleResult();
            studentInfo = new StudentInfoDTO(student);
        } finally {
            em.close();
        }

        return studentInfo;
    }

    private List<StudentInfoDTO> getStudentsInfo() {
        EntityManager em = getEntityManager();
        List<StudentInfoDTO> studentInfoDTOList = new ArrayList<>();

        try {
            //String firstName, String lastName, long studentId, String classNameThisSemester, String classDescription
            TypedQuery<Student> query = em.createQuery(
                    "select stu from Student stu ",
                    Student.class);
            List<Student> students = query.getResultList();
            studentInfoDTOList = new StudentInfoDTO(students).getAll();
//            students.forEach((student) ->{
//                studentInfoDTOList.add(new StudentInfoDTO(student));
//            });
        } finally {
            em.close();
        }

        return studentInfoDTOList;
    }

    private Semester getSemesterWithFewestStudents() {
        //!!!Avert your eyes Black magic ahead.
        EntityManager em = getEntityManager();
        Semester s;
//        List<Semester> sems = new ArrayList<>();
//        try {
//            TypedQuery<Semester> query = em.createQuery(
//                    "select sem from Semester sem join sem.students stu group by sem.id order by count (sem.id) asc ",
//                    Semester.class);
//            sems = query.getResultList();
//            System.out.print("");
//        } finally {
//            em.close();
//        }
        try {
            TypedQuery<Semester> query = em.createQuery(
                    "select sem from Semester sem join sem.students stu group by sem.id order by count (sem.id) asc ",
                    Semester.class);
            query.setMaxResults(1);
            s = query.getSingleResult();
        } finally {
            em.close();
        }

        return s;
    }

    private Teacher getTeacherWhoTeachesMostSemesters() {
        //!!!Avert your eyes Black magic ahead.
        EntityManager em = getEntityManager();
        Teacher t;
        try {
            TypedQuery<Teacher> query = em.createQuery(
                    "select t from Teacher t join t.semesters sem group by t.id order by count (t.id) desc ",
                    Teacher.class);
            query.setMaxResults(1);
            t = query.getSingleResult();
            System.out.print("");
        } finally {
            em.close();
        }

        return t;
    }

    private int getStudentCountByTeacher(Teacher teacher) {
        EntityManager em = getEntityManager();
        List<Student> students = new ArrayList<>();

        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT stu FROM Student stu JOIN stu.semester sem JOIN sem.teachers tea WHERE tea.id = :teacherId",
                    Student.class);
            query.setParameter("teacherId", teacher.getId());
            students = query.getResultList();
            System.out.print("");
        } finally {
            em.close();
        }
        return students.size();
    }

    private int getStudentCountByTeacher(String fname) {
        EntityManager em = getEntityManager();
        List<Student> students = new ArrayList<>();

        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT stu FROM Student stu JOIN stu.semester sem JOIN sem.teachers tea WHERE tea.firstname = :fname",
                    Student.class);
            query.setParameter("fname", fname);
            students = query.getResultList();  //TODO:Kan man ikke bare nøjes med at få antallet af rows?
            System.out.print("");
        } finally {
            em.close();
        }
        return students.size();
    }

    private int getStudentCountByTeacher(int id) {
        EntityManager em = getEntityManager();
        List<Student> students = new ArrayList<>();

        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT stu FROM Student stu JOIN stu.semester sem JOIN sem.teachers tea WHERE tea.firstname = :id",
                    Student.class);
            query.setParameter("id", id);
            students = query.getResultList();  //TODO:Kan man ikke bare nøjes med at få antallet af rows?
            System.out.print("");
        } finally {
            em.close();
        }
        return students.size();
    }

    private int getStudentCountBySemesterName(String semesterName) {
        EntityManager em = getEntityManager();
        List<Student> students = new ArrayList<>();

        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT COUNT(stu) FROM Student stu JOIN stu.semester sem WHERE sem.name = :sname",
                    Student.class);
            query.setParameter("sname", semesterName);
            students = query.getResultList();
        } finally {
            em.close();
        }
        return students.size();
    }

    private List<Student> getAllStudentsByLastName(String lastName) {
        EntityManager em = getEntityManager();
        List<Student> students = new ArrayList<>();

        try {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.lastname = :lname", Student.class);
            query.setParameter("lname", lastName);
            students = query.getResultList();
        } finally {
            em.close();
        }
        return students;
    }

    private List<Student> getAllStudentsByFirstName(String firstName) {
        EntityManager em = getEntityManager();
        List<Student> students = new ArrayList<>();

        try {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.firstname = :fname", Student.class);
            query.setParameter("fname", firstName);
            students = query.getResultList();
        } finally {
            em.close();
        }
        return students;
    }

    private Teacher getTeacherById(Long id) {
        EntityManager em = getEntityManager();
        Teacher teacher = em.find(Teacher.class, id);
        em.close();
        return teacher;
    }

    @Override
    public List<Student> getAllStudents() {
        EntityManager em = getEntityManager();
        List<Student> students;
        try {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s", Student.class);
            students = query.getResultList();
        } finally {
            em.close();
        }
        return students;
    }

    @Override
    public Student createStudent(Student student) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return student;
    }

    @Override
    public Semester createSemester(Semester semester) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(semester);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return semester;
    }

    @Override
    public Teacher createTeacher(Teacher teacher) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(teacher);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return teacher;
    }

    @Override
    public Semester addSutdentToSemester(Student student, Semester semester) {
        semester.addStudents(student);
        return semester;
    }

    @Override
    public Semester addTeacherToSemester(Teacher teacher, Semester semester) {
        semester.addTeacher(teacher);
        return semester;
    }

    @Override
    public Semester removeTeacherFromSemester(Teacher teacher, Semester semester) {
//        EntityManager em = getEntityManager();
//        Teacher found;
////        try {
////            found = em.find(Teacher.class, teacher.getId());
////            em.getTransaction().begin();
////            em.remove(found);
////            em.getTransaction().commit();
////        }finally {
////            em.close();
////        }
//
//        try {
//            Query query = em.createQuery("delete from TEACHER_SEMESTER where teaching_ID = :semesterId and teachers_ID = :teacherId;");
//            query.setParameter("semesterId", semester.getId());
//            query.setParameter("teacherId", teacher.getId());
//            int deletecount = query.executeUpdate();
//            System.out.println();
//        } finally {
//            em.close();
//        }
//
////        try {
////            TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c WHERE c.lastName = :lastName", Customer.class);
////            query.setParameter("lastName", lastName);
////            customers = query.getResultList();
////        }finally {
////            em.close();
////        }
        semester.removeTeacher(teacher);
        return semester;
    }

    @Override
    public Semester updateSemester(Semester semester, String name, String description) {
        semester.setDescription(description);
        semester.setName(name);
        return updateSemester(semester);
    }

    @Override
    public Set<Student> getAllStudentsBySemester(Semester semester) {
        return semester.getStudents();
    }

    public Set<Student> getAllStudentsBySemester(long id) {
        EntityManager em = getEntityManager();
        Semester s = em.find(Semester.class, id);
        em.close();
        return s.getStudents();
    }

    public Set<Student> getAllStudentsBySemester(String name) {
        EntityManager em = getEntityManager();
        Semester s = em.find(Semester.class, name);
        em.close();
        return s.getStudents();
    }

    @Override
    public Set<Student> getAllStudentsByTeacher(Teacher teacher) {
        Set<Student> students = new HashSet<>();
        teacher.getSemesters().forEach((semester) -> {
            semester.getStudents().forEach((student) -> {
                students.add(student);
            });
        });
        return students;
    }

    public Set<Student> getAllStudentsByTeacher(long id) {
        EntityManager em = getEntityManager();
        Teacher t = em.find(Teacher.class, id);
        em.close();
        return getAllStudentsByTeacher(t);
    }

    public Set<Student> getAllStudentsByTeacher(String name) {
        EntityManager em = getEntityManager();
        Teacher t = em.find(Teacher.class, name);
        em.close();
        return getAllStudentsByTeacher(t);
    }

    public Semester updateSemester(Semester s) {
        EntityManager em = getEntityManager();
        Semester merged;
        try {
            em.getTransaction().begin();
            merged = em.merge(s);
            s.getStudents().forEach((student) -> {
                em.merge(student);
            });
            s.getTeachers().forEach((teacher) -> {
                em.merge(teacher);
            });
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return merged;
    }

}
