package com.vismee.advancedhibernatemapping.dao;

import com.vismee.advancedhibernatemapping.entity.Course;
import com.vismee.advancedhibernatemapping.entity.Instructor;
import com.vismee.advancedhibernatemapping.entity.InstructorDetail;
import com.vismee.advancedhibernatemapping.entity.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO
{

    private EntityManager entityManager;

    @Autowired
    public AppDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void saveInstructor(Instructor instructor) {
        entityManager.persist(instructor);
    }

    @Override
    public Instructor findInstructorById(int id) {
        return entityManager.find(Instructor.class,id);
    }

    @Override
    @Transactional
    public void deleteInstructorById(int id) {
        Instructor instructor = entityManager.find(Instructor.class,id);

        List<Course> courses = instructor.getCourses();
        for(Course course:courses)
        {
            course.setInstructor(null);
        }

        entityManager.remove(instructor);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int id) {
        return entityManager.find(InstructorDetail.class,id);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int id) {
        InstructorDetail instructorDetail = entityManager.find(InstructorDetail.class,id);
        instructorDetail.getInstructor().setInstructorDetail(null);
        entityManager.remove(instructorDetail);
    }

    @Override
    @Transactional
    public void createCourse(Course course,int id) {
        Instructor instructor = entityManager.find(Instructor.class,id);
        course.setInstructor(instructor);

        entityManager.persist(course);
    }

    @Override
    public List<Course> findCoursesByInstructorId(int id) {
        TypedQuery<Course> query = entityManager.createQuery("from Course where instructor.id = :data", Course.class);
        query.setParameter("data",id);

        List<Course> courses = query.getResultList();
        return courses;
    }

    @Override
    public Instructor findInstructorWithCoursesJoinFetch(int id)
    {
        TypedQuery<Instructor> query = entityManager.createQuery("from Instructor i " +
                                                                 "JOIN FETCH i.courses " +
                                                                  "where i.id=:data", Instructor.class);
        query.setParameter("data",id);
        Instructor instructor = query.getSingleResult();
        return instructor;
    }

    @Override
    @Transactional
    public void updateInstructor(Instructor instructor) {
        entityManager.merge(instructor);
    }

    @Override
    public Course findCourseById(int id) {
        Course course = entityManager.find(Course.class,id);
        return course;
    }

    @Override
    @Transactional
    public void updateCourse(Course course) {
        entityManager.merge(course);
    }

    @Override
    @Transactional
    public void deleteCourseById(int id) {
        Course course = entityManager.find(Course.class,id);

        entityManager.remove(course);
    }

    @Override
    @Transactional
    public void createCourseWithReviews(Course course) {
        entityManager.persist(course);
    }

    @Override
    public Course findCoursesAndReviews(int id) {
        TypedQuery<Course> query = entityManager.createQuery("from Course c " +
                                                              "JOIN FETCH c.reviews " +
                                                               "where c.id=:data", Course.class);
        query.setParameter("data",id);
        Course course = query.getSingleResult();

        return course;
    }

    @Override
    @Transactional
    public void deleteReviewById(int id) {
        Review review = entityManager.find(Review.class,id);
        entityManager.remove(review);
    }

    @Override
    @Transactional
    public void addReviewForCourse(Review review, int id) {
         Course course = entityManager.find(Course.class,id);
         course.addReview(review);
         entityManager.persist(course);
    }
}
