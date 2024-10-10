import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The University course management system.
 */
public class UniversityCourseManagementSystem {
    /**
     * Array with all courses
     */
    public static List<Course> allCourses = new ArrayList<>();
    /**
     * Array with all students and professors
     */
    public static List<UniversityMember> allMembers = new ArrayList<>();
    /**
     * Array with names of all courses
     */
    public static List<String> coursesNames = new ArrayList<>();
    /**
     * String with all commands that is used to chack validity of inputs
     */
    public static String commands = "coursestudentprofessorenrolldropteachexempt";

    /**
     * Is alpha boolean is a boolean function that checks if name contains only english letters
     *
     * @param name the name
     * @return the boolean
     */
    public static boolean isAlpha(String name) {
        return name != null && name.matches("^[a-zA-Z]*$");
    }

    /**
     * Check course name boolean is a boolean function that checks validity of courses name
     *
     * @param name the name
     * @return the boolean
     */
    public static boolean checkCourseName(String name) {
        if (name.contains("_")) {
            String[] parts = name.split("_");
            for (String part : parts) {
                if (!part.isEmpty() && isAlpha(part)) {
                    return true;
                }
            }
        } else {
            return !name.isEmpty();
        }
        return true;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        fillInitialData();
        Scanner scanner = new Scanner(System.in);

        try {
            while (scanner.hasNextLine()) {
                String cmd = scanner.nextLine().toLowerCase();
                if (commands.contains(cmd)) {
                    handleCommand(cmd, scanner);
                } else {
                    System.out.println("Wrong inputs");
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            System.out.println("Wrong inputs");
            System.exit(0);
        }
    }

    /**
     * Handle command is a function that handles input commands
     *
     * @param cmd     the cmd
     * @param scanner the scanner
     */
    public static void handleCommand(String cmd, Scanner scanner) {
        switch (cmd) {
            case "course":
                createCourse(scanner);
                break;
            case "student":
                createStudent(scanner);
                break;
            case "professor":
                createProfessor(scanner);
                break;
            case "enroll":
                doEnrollCommand(scanner);
                break;
            case "drop":
                doDropCommand(scanner);
                break;
            case "teach":
                doTeachCommand(scanner);
                break;
            case "exempt":
                doExemptCommand(scanner);
                break;
        }
    }

    /**
     * Create course is a function that creates new course
     *
     * @param scanner the scanner
     */
    public static void createCourse(Scanner scanner) {
        String name = scanner.next();
        if (!name.isEmpty()) {
            if (coursesNames.contains(name)) {
                System.out.println("Course exists");
                System.exit(0);
            } else if (!checkCourseName(name)) {
                System.out.println("Wrong inputs");
                System.exit(0);
            }
            String level = scanner.next();
            if (!level.isEmpty()) {
                String levels = "masterbachelor";
                if (levels.contains(level)) {
                    CourseLevel courseLevel = level.equalsIgnoreCase("master") ? CourseLevel.MASTER : CourseLevel.BACHELOR;
                    Course newCourse = new Course(name, courseLevel);
                    allCourses.add(newCourse);
                    coursesNames.add(name);
                    System.out.println("Added successfully");
                } else {
                    System.out.println("Wrong inputs");
                    System.exit(0);
                }
            }
        }
    }

    /**
     * Create student is a function that creates new student
     *
     * @param scanner the scanner
     */
    public static void createStudent(Scanner scanner) {
        String name = scanner.next();
        if (isAlpha(name) && !commands.contains(name)) {
            Student newStudent = new Student(name);
            allMembers.add(newStudent);
            System.out.println("Added successfully");
        } else {
            System.out.println("Wrong inputs");
            System.exit(0);
        }
    }

    /**
     * Create professor is a function that creates new professor
     *
     * @param scanner the scanner
     */
    public static void createProfessor(Scanner scanner) {
        String name = scanner.next();
        if (isAlpha(name) && !commands.contains(name)) {
            Professor newProfessor = new Professor(name);
            allMembers.add(newProfessor);
            System.out.println("Added successfully");
        } else {
            System.out.println("Wrong inputs");
            System.exit(0);
        }
    }

    /**
     * Do enroll command is a function that performs enroll command
     *
     * @param scanner the scanner
     */
    public static void doEnrollCommand(Scanner scanner) {
        int memberId = scanner.nextInt();
        int courseId = scanner.nextInt();
        if (checkIdValidity(memberId, courseId) && allMembers.get(memberId - 1) instanceof Student) {
            ((Student) allMembers.get(memberId - 1)).enroll(allCourses.get(courseId - 1), 1);
        } else {
            System.out.println("Wrong inputs");
            System.exit(0);
        }
    }

    /**
     * Do drop command is a function that performs drop command
     *
     * @param scanner the scanner
     */
    public static void doDropCommand(Scanner scanner) {
        int memberId = scanner.nextInt();
        int courseId = scanner.nextInt();
        if (checkIdValidity(memberId, courseId) && allMembers.get(memberId - 1) instanceof Student) {
            ((Student) allMembers.get(memberId - 1)).drop(allCourses.get(courseId - 1), 1);
        } else {
            System.out.println("Wrong inputs");
            System.exit(0);
        }
    }

    /**
     * Do teach command is a function that performs teach command
     *
     * @param scanner the scanner
     */
    public static void doTeachCommand(Scanner scanner) {
        int memberId = scanner.nextInt();
        int courseId = scanner.nextInt();
        if (checkIdValidity(memberId, courseId) && allMembers.get(memberId - 1) instanceof Professor) {
            ((Professor) allMembers.get(memberId - 1)).teach(allCourses.get(courseId - 1), 1);
        } else {
            System.out.println("Wrong inputs");
            System.exit(0);
        }
    }

    /**
     * Do exempt command is a function that performs exempt command
     *
     * @param scanner the scanner
     */
    public static void doExemptCommand(Scanner scanner) {
        int memberId = scanner.nextInt();
        int courseId = scanner.nextInt();
        if (checkIdValidity(memberId, courseId) && allMembers.get(memberId - 1) instanceof Professor) {
            ((Professor) allMembers.get(memberId - 1)).exempt(allCourses.get(courseId - 1), 1);
        } else {
            System.out.println("Wrong inputs");
            System.exit(0);
        }
    }

    /**
     * Check id validity boolean function is a function that checks validity of Id
     *
     * @param memberId the member id
     * @param courseId the course id
     * @return the boolean
     */
    public static boolean checkIdValidity(int memberId, int courseId) {
        return memberId > 0 && memberId <= allMembers.size() && courseId > 0 && courseId <= allCourses.size();
    }

    /**
     * Fill initial data is a function that creates initial students, professors and courses
     */
    public static void fillInitialData() {
        Course javaBeginner = new Course("java_beginner", CourseLevel.BACHELOR);
        coursesNames.add("java_beginner");
        Course javaIntermediate = new Course("java_intermediate", CourseLevel.BACHELOR);
        coursesNames.add("java_intermediate");
        Course pythonBasics = new Course("python_basics", CourseLevel.BACHELOR);
        coursesNames.add("python_basics");
        Course algorithms = new Course("algorithms", CourseLevel.MASTER);
        coursesNames.add("algorithms");
        Course advancedProgramming = new Course("advanced_programming", CourseLevel.MASTER);
        coursesNames.add("advanced_programming");
        Course mathematicalAnalysis = new Course("mathematical_analysis", CourseLevel.MASTER);
        coursesNames.add("mathematical_analysis");
        Course computerVision = new Course("computer_vision", CourseLevel.MASTER);
        coursesNames.add("computer_vision");
        Student alice = new Student("Alice");
        Student bob = new Student("Bob");
        Student alex = new Student("Alex");
        Professor ali = new Professor("Ali");
        Professor ahmed = new Professor("Ahmed");
        Professor andrey = new Professor("Andrey");
        allCourses.add(javaBeginner);
        allCourses.add(javaIntermediate);
        allCourses.add(pythonBasics);
        allCourses.add(algorithms);
        allCourses.add(advancedProgramming);
        allCourses.add(mathematicalAnalysis);
        allCourses.add(computerVision);
        alice.enroll(javaBeginner, 0);
        alice.enroll(javaIntermediate, 0);
        alice.enroll(pythonBasics, 0);
        bob.enroll(javaBeginner, 0);
        bob.enroll(algorithms, 0);
        alex.enroll(advancedProgramming, 0);
        ali.teach(javaBeginner, 0);
        ali.teach(javaIntermediate, 0);
        ahmed.teach(pythonBasics, 0);
        ahmed.teach(advancedProgramming, 0);
        andrey.teach(mathematicalAnalysis, 0);
        allMembers.add(alice);
        allMembers.add(bob);
        allMembers.add(alex);
        allMembers.add(ali);
        allMembers.add(ahmed);
        allMembers.add(andrey);
    }

    /**
     * The enum Course level.
     */
    enum CourseLevel {
        /**
         * Bachelor course level.
         */
        BACHELOR,
        /**
         * Master course level.
         */
        MASTER
    }

    /**
     * The interface Enrollable.
     */
    interface Enrollable {
        /**
         * Drop boolean.
         *
         * @param course the course
         * @param pf     the pf
         * @return the boolean
         */
        boolean drop(Course course, int pf);

        /**
         * Enroll boolean.
         *
         * @param course the course
         * @param pf     the pf
         * @return the boolean
         */
        boolean enroll(Course course, int pf);
    }

    /**
     * The University member class
     */
    abstract static class UniversityMember {
        private static int numberOfMembers;
        private int memberId;
        private String memberName;

        /**
         * Instantiates a new University member.
         *
         * @param memberName the member name
         */
        public UniversityMember(String memberName) {
            this.memberName = memberName;
            numberOfMembers++;
            this.memberId += numberOfMembers;
        }
    }

    /**
     * The Course class
     */
    static class Course {
        private static final int THREE = 3;
        private final int capacity = THREE;
        private int numberOfCourses;
        private int courseId = 0;
        private String courseName;
        private List<Student> enrolledStudents = new ArrayList<>();
        private CourseLevel courseLevel;

        /**
         * Instantiates a new Course.
         *
         * @param courseName  the course name
         * @param courseLevel the course level
         */
        public Course(String courseName, CourseLevel courseLevel) {
            this.courseName = courseName;
            this.courseLevel = courseLevel;
            courseId++;
            numberOfCourses++;
        }

        /**
         * Is full boolean.
         *
         * @return the boolean
         */
        public boolean isFull() {
            return enrolledStudents.toArray().length >= THREE;
        }
    }

    /**
     * The Student class
     */
    static class Student extends UniversityMember implements Enrollable {
        private static final int MAX_ENROLLMENT = 2 + 1;
        private final List<Course> enrolledCourses = new ArrayList<>();

        /**
         * Instantiates a new Student.
         *
         * @param memberName the member name
         */
        public Student(String memberName) {
            super(memberName);
        }

        @Override
        public boolean drop(Course course, int pf) {
            if (enrolledCourses.contains(course)) {
                if (pf == 1) {
                    enrolledCourses.remove(course);
                    System.out.print("Dropped successfully\n");
                    return true;
                } else {
                    enrolledCourses.remove(course);
                    return true;
                }
            } else {
                if (pf == 1) {
                    System.out.print("Student is not enrolled in this course\n");
                    System.exit(0);
                    return false;
                } else {
                    System.exit(0);
                    return false;
                }
            }
        }

        @Override
        public boolean enroll(Course course, int pf) {
            if (!enrolledCourses.contains(course)) {
                if (enrolledCourses.toArray().length < MAX_ENROLLMENT) {
                    if (!course.isFull()) {
                        if (pf == 1) {
                            enrolledCourses.add(course);
                            System.out.print("Enrolled successfully\n");
                            return true;
                        } else {
                            enrolledCourses.add(course);
                            return true;
                        }
                    }
                } else {
                    if (pf == 1) {
                        System.out.print("Maximum enrollment is reached for the student\n");
                        System.exit(0);
                        return false;
                    } else {
                        System.exit(0);
                        return false;
                    }
                }
            } else {
                if (pf == 1) {
                    System.out.print("Student is already enrolled in this course\n");
                    System.exit(0);
                    return false;
                } else {
                    System.exit(0);
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * The Professor class
     */
    static class Professor extends UniversityMember {
        private int MAX_LOAD = 2;
        private final List<Course> assignedCourses = new ArrayList<>();

        /**
         * Instantiates a new Professor.
         *
         * @param memberName the member name
         */
        public Professor(String memberName) {
            super(memberName);
        }

        /**
         * Teach boolean.
         *
         * @param course the course
         * @param pf     the pf
         * @return the boolean
         */
        public boolean teach(Course course, int pf) {
            if (!assignedCourses.contains(course)) {
                if (assignedCourses.size() < MAX_LOAD) {
                    if (pf == 1) {
                        assignedCourses.add(course);
                        System.out.print("Professor is successfully assigned to teach this course\n");
                        return true;
                    } else {
                        assignedCourses.add(course);
                        return true;
                    }
                } else {
                    if (pf == 1) {
                        System.out.print("Professor's load is complete\n");
                        System.exit(0);
                        return true;
                    } else {
                        System.exit(0);
                        return true;
                    }
                }
            } else {
                if (pf == 1) {
                    System.out.print("Professor is already teaching this course\n");
                }
                System.exit(0);
                return false;
            }
        }

        /**
         * Exempt boolean.
         *
         * @param course the course
         * @param pf     the pf
         * @return the boolean
         */
        public boolean exempt(Course course, int pf) {
            if (assignedCourses.contains(course)) {
                assignedCourses.remove(course);
                if (pf == 1) {
                    System.out.print("Professor is exempted\n");
                    return true;
                }
                return true;
            } else {
                if (pf == 1) {
                    System.out.println("Professor is not teaching this course\n");
                    return false;
                }
                System.exit(0);
                return false;
            }
        }
    }
}
