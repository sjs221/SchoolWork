package edu.yu.registrar;
import edu.yu.registrar.model.*;

import java.util.List;
import java.util.Set;

public class System {

    public System(String registrar, String university) {
    }

    public void register(String user, String stud, String courseOffering){

    }

    public Course drop(String user, String stud, String courseOffering){
        return null;
    }

    public Set<Course> searchCourses (String user, String dept){
        return null;
    }

    public Set<Professor> searchProfs (String user, String dept){
        return null;
    }

    public Double getGrade (String user, String stud, String courseOffering){
        return null;
    }

    public void setGrade(String user, String student, String courseOffering, int grade){

    }

    public void addCourse(String user, String school, String dept, String course){

    }

    public void addCourseOffering(String user, String school, String courseOffering, String prof){

    }

    public void setProfessor(String user, String prof, String courseOffering) {
    }

    public List<Student> getStudentsEnrolled(String user) {
        return null;
    }

    public List<Student> getStudentsEnrolled(String user, String courseOffering) {
        return null;
    }

    public double getAverage(String user, String courseOffered) {
        return 0;
    }

    public CourseOffering searchCourseOfferings(String user, String dept, String course) {
        return null;
    }

    public Department searchDeptartments(String user, String dept) {
        return null;
    }

    public void addSchool(String user, String school, String dean) {
    }

    public void addDept(String user, String school, String dept, String deptHead) {

    }
}
