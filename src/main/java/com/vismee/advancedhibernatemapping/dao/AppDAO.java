package com.vismee.advancedhibernatemapping.dao;

import com.vismee.advancedhibernatemapping.entity.Course;
import com.vismee.advancedhibernatemapping.entity.Instructor;
import com.vismee.advancedhibernatemapping.entity.InstructorDetail;
import com.vismee.advancedhibernatemapping.entity.Review;

import java.util.List;

public interface AppDAO
{
    void saveInstructor(Instructor instructor);
    Instructor findInstructorById(int id);
    void deleteInstructorById(int id);
    InstructorDetail findInstructorDetailById(int id);
    void deleteInstructorDetailById(int id);
    void createCourse(Course course,int id);
    List<Course> findCoursesByInstructorId(int id);
    Instructor findInstructorWithCoursesJoinFetch(int id);
    void updateInstructor(Instructor instructor);
    Course findCourseById(int id);
    void updateCourse(Course course);
    void deleteCourseById(int id);
    void createCourseWithReviews(Course course);
    Course findCoursesAndReviews(int id);
    void deleteReviewById(int id);
    void addReviewForCourse(Review review,int id);
}
