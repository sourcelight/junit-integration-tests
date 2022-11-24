package edu.riccardo.studentservice;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
public class StudentCacheOnlyMockitoTest
{

    @Mock
    private StudentRepository studentRepository;

    // @InjectMocks doesn't create the real application spring context for the bean StudentService
    //it just instantiate the class and set the previous mock created, so the @Cacheable annotation doesn't work
    // In case I want really test that bean/service I need to @Autowire it so that SpringBootTest actually instantiate it according the spring context
    // and then inject the repository with the @MockBean annotation.
    @InjectMocks
    private StudentService studentService;

    @Test
    void getStudentById_forMultipleRequests_isRetrievedFromCache()
    {
        //given
        Long id = 123l;

        given(studentRepository.findById(id)).willReturn(Optional.of(new Student(id, "Mark")));

        //when
        studentService.getStudentById(id);
        studentService.getStudentById(id);
        studentService.getStudentById(id);

        //then
        then(studentRepository).should(times(1)).findById(id);
    }
}
