package edu.riccardo.studentservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
public class StudentRepositoryTest
{

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    //@Rollback(false)
    void testGetStudentByName_returnsStudentDetails()
    {
        //given
        Student savedStudent = testEntityManager.persistFlushFind(new Student(null, "Johan"));


        //when
        Student student = studentRepository.getStudentByName("Johan");

        //then
        then(student.getId()).isNotNull();
        then(student.getName()).isEqualTo(savedStudent.getName());
    }

    @Test
    void getAvgGradeForActiveStudents_calculatesAvg()
    {
        //given
        //Student johan = Student.builder().name("Johan").active(true).grade(80).build();
        Student johan = Student.builder().name("Johan").active(true).grade(80).build();
        Student susan = Student.builder().name("Susan").active(true).grade(100).build();
        Student peter = Student.builder().name("Peter").active(false).grade(50).build();
        Arrays.asList(johan, susan, peter).forEach(testEntityManager::persistFlushFind);
//       Arrays.asList(johan, susan, peter).forEach(studentRepository::save);

        //when
        Double avgGrade = studentRepository.getAvgGradeForActiveStudents();

        //then
        then(avgGrade).isEqualTo(90);
    }

}
