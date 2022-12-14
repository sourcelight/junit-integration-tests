package edu.riccardo.studentservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
public class StudentCacheTest
{

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    void getStudentById_forMultipleRequests_isRetrievedFromCache()
    {
        //given
        Long id = 123l;
        //we mock "studentRepository"
        given(studentRepository.findById(id)).willReturn(Optional.of(new Student(id, "Mark")));

        //when
        studentService.getStudentById(id);
        studentService.getStudentById(id);
        studentService.getStudentById(id);

        //then(we test that actually we call the studentRepository only the first time)
        then(studentRepository).should(times(1)).findById(id);
    }
}
