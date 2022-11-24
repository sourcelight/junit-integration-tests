package edu.riccardo.studentservice;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

//@SpringBootTest(webEnvironment = NONE)
@SpringBootTest(webEnvironment = NONE)//we prevent starting the web environment
//@SpringBootTest(webEnvironment = DEFINED_PORT, properties = "server.port=8180" ) //I can set a specific port

//Transactional annotation prevents you can see the results of the queries in the console and the result on the h2 console
//because it isolates completely the transaction of this test from another one.
@Transactional
public class StudentServiceTest
{

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;


    @DisplayName("Returning saved student from service layer")
    @Test
    //@Rollback(false)
    void getStudentById_forSavedStudent_isReturned()
    {
        //given
        Student savedStudent = studentRepository.save(new Student(null, "Mark"));
        studentRepository.flush();

        studentRepository.delete(savedStudent);
        //when
        Student retrievedStudent = studentService.getStudentById(savedStudent.getId());

        //then
        then(retrievedStudent.getName()).isEqualTo("Mark");
        then(retrievedStudent.getId()).isNotNull();

    }

    @Test
    void getStudentById_whenMissingStudent_notFoundExceptionThrown()
    {
        //given
        Long id = 1234l;
        //when
        Throwable throwable = catchThrowable(() -> studentService.getStudentById(id));

        //then
        BDDAssertions.then(throwable).isInstanceOf(StudentNotFoundException.class);
    }

}



