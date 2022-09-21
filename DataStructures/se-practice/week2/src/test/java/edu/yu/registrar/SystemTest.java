package edu.yu.registrar;

import edu.yu.registrar.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SystemTest {

    @Test
    public void testSearchStuds(){
        System sys = new System("WilfRegistrar","Yeshiva University");
        sys.addSchool("WilfRegistrar", "YC", "YC_Dean");
        sys.addDept("WilfRegistrar", "YC","CS","Judah");
        sys.addCourse("WilfRegistrar","YU","CS", "Intro");
        sys.addCourse("WilfRegistrar","YU","CS", "Data Structures");
        sys.addCourseOffering("WilfRegistrar","YC", "Intro_Fall2020_MW", "Judah");
        sys.addCourseOffering("WilfRegistrar","YC", "DS_Spring2021_TR", "Judah");
        sys.addCourseOffering("WilfRegistrar","YC", "Intro_Fall2020_TR", "Wymore");
        sys.addCourseOffering("WilfRegistrar","YC", "DS_Spring2021_MW", "Wymore");
        sys.register("WilfRegistrar","Sam", "Intro_Fall2020_MW");
        sys.register("WilfRegistrar","Sam", "DS_Spring2021_TR");
        sys.register("WilfRegistrar","Jake", "Intro_Fall2020_TR");
        sys.register("WilfRegistrar","Jake", "DS_Spring2021_TR");
        sys.register("WilfRegistrar","Gabe", "Intro_Fall2020_MW");

        assertEquals(3, sys.getStudentsEnrolled("WilfRegistrar").size());
        assertEquals(2, sys.getStudentsEnrolled("WilfRegistrar","Intro_Fall2020_MW").size());
        assertEquals(1, sys.getStudentsEnrolled("WilfRegistrar","Intro_Fall2020_TR").size());

        sys.drop("WilfRegistrar","Jake", "Intro_Fall2020_TR");
        sys.drop("WilfRegistrar","Jake", "DS_Spring2021_TR");
        sys.register("WilfRegistrar","Jake", "Intro_Fall2020_MW");
        sys.register("WilfRegistrar","Jake", "DS_Spring2021_TR");

        assertEquals(3, sys.getStudentsEnrolled("WilfRegistrar","Intro_Fall2020_MW").size());
        assertEquals(0, sys.getStudentsEnrolled("WilfRegistrar","Intro_Fall2020_TR").size());

        sys.setGrade("Judah", "Sam","Intro_Fall2020_MW", 0);
        sys.setGrade("Judah", "Gabe","Intro_Fall2020_MW", 100);
        sys.setGrade("Judah", "Jake","Intro_Fall2020_MW", 50);

        assertEquals(50, sys.getAverage("WilfRegistrar","Intro_Fall2020_MW"));
    }

    @Test
    public void testSecurity(){
        System sys = new System("WilfRegistrar","Yeshiva University");
        sys.addSchool("WilfRegistrar", "YC", "YC_Dean");
        sys.addDept("WilfRegistrar", "YC","CS","Judah");
        sys.addCourse("WilfRegistrar","YU","CS", "Intro");
        sys.addCourse("WilfRegistrar","YU","CS", "Data Structures");
        sys.addCourseOffering("WilfRegistrar","YC", "Intro_Fall2020_MW", "Judah");
        sys.addCourseOffering("WilfRegistrar","YC", "DS_Spring2021_TR", "Judah");
        sys.addCourseOffering("WilfRegistrar","YC", "Intro_Fall2020_TR", "Wymore");
        sys.addCourseOffering("WilfRegistrar","YC", "DS_Spring2021_MW", "Wymore");
        sys.register("WilfRegistrar","Sam", "Intro_Fall2020_MW");
        sys.register("WilfRegistrar","Sam", "DS_Spring2021_TR");
        sys.register("WilfRegistrar","Jake", "Intro_Fall2020_TR");
        sys.register("WilfRegistrar","Jake", "DS_Spring2021_TR");
        sys.register("WilfRegistrar","Gabe", "Intro_Fall2020_MW");

        sys.setGrade("Judah", "Sam","Intro_Fall2020_MW", 66);
        sys.setGrade("Sam", "Sam","Intro_Fall2020_MW", 100);

        assertEquals(66, sys.getGrade("Sam", "Sam","Intro_Fall2020_MW"));

        sys.addCourseOffering("WilfRegistrar","Syms", "Accounting_Fall2021", "Dahkli");

        boolean accessDenied1 = false;
        try{
            sys.getGrade("Dahkli", "Sam","Intro_Fall2020_MW");
        }catch (UnauthorizedActionException e){
            accessDenied1 = true;
        }
        assertTrue(accessDenied1);

        boolean accessDenied2 = false;
        try{
            sys.setGrade("Dahkli", "Sam","Intro_Fall2020_MW",40);
        }catch (UnauthorizedActionException e){
            accessDenied2 = true;
        }
        assertTrue(accessDenied2);
        boolean accessDenied3 = false;
        try{
            sys.setGrade("Sam", "Sam","Intro_Fall2020_MW",40);
        }catch (UnauthorizedActionException e){
            accessDenied3 = true;
        }
        assertTrue(accessDenied3);
    }

    @Test
    public void searchTest(){
        //make system of 5 depts, 10 courses each, 10 students per
        System sys = new System("WilfRegistrar","Yeshiva University");
        sys.addSchool("WilfRegistrar", "YC", "YC_Dean");
        for(int i=0;i<5;i++){
            sys.addDept("WilfRegistrar", "YC","dept_" + i,"Prof_" + i);
            for(int j=0;j<10;j++){
                sys.addCourse("WilfRegistrar","YC", "dept_" + i, "course_" + j);
                sys.addCourseOffering("WilfRegistrar","YC", "course_" + j + "_Fall2021", "Prof_" + j);
                for(int k=0;k<10;k++){
                    sys.register("WilfRegistrar","stud_" + k + "_" + j, "course_" + j);
                }
            }
        }

        Object testObject = null;
        testObject = sys.searchCourseOfferings("WilfRegistrar","dept_2","course_3");
        assertTrue(testObject instanceof CourseOffering);

        testObject = sys.searchCourseOfferings("stud_3_2","dept_2","course_3");
        assertTrue(testObject instanceof ImmutableCourseOffering);

        testObject = sys.searchDeptartments("stud_3_2","dept_2");
        assertTrue(testObject instanceof ImmutableDepartment);

        testObject = sys.searchDeptartments("Prof_3","dept_2");
        assertTrue(testObject instanceof ImmutableDepartment);

        testObject = sys.searchDeptartments("Prof_1","dept_1");
        assertTrue(testObject instanceof Department);

        testObject = sys.searchDeptartments("YC_Dean","dept_1");
        assertTrue(testObject instanceof Department);
    }

}