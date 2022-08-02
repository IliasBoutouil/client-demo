package com.valueIt.demo.repos.students;

import com.valueIt.demo.entities.students.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {

}
