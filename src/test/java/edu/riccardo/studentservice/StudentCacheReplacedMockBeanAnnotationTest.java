package edu.riccardo.studentservice;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = DEFINED_PORT)
public class StudentCacheReplacedMockBeanAnnotationTest
{

    @Autowired
    private StudentService studentService;

    //@MockBean
   // @Mock
    //I cannot replace @MockBean with @Mock and after inject this in the StudentService with "@InjectMocks"
    //because "StudentService" has to be a real Bean from spring and it needs to be autowired.
    //private StudentRepository studentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void getStudentById_forMultipleRequests_isRetrievedFromCache()
    {
        //given
        Long id = 123l;
        //given(studentRepository.findById(id)).willReturn(Optional.of(new Student(id, "Mark")));
        // if studentRepository was a mock doesn't work because it's not inserting a real record in the DB
        //and later the " studentService.getStudentById(id);" wouldn't extract any record
        //Given
        //In the simplest cache example the repository is simulated with mockito and so the id is specified,
        //in this case it's generated from the real H2 DB( @Id @GeneratedValue) and so I have to use
        //that one from the DB
        Student student = studentRepository.saveAndFlush(new Student(id, "Mark"));
        //when
        Long idDB = student.getId();
        studentService.getStudentById(idDB);
        studentService.getStudentById(idDB);
        studentService.getStudentById(idDB);

        //then
        //In any case this count doesn't work because "studentRepository" should be a MockBean to be counted its occurrences
        then(studentRepository).should(times(1)).findById(id);
    }
}
