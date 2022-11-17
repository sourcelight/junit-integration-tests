package edu.riccardo.studentservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StudentController
{
    private final StudentService studentService;

    @GetMapping("/students/{id}")
    Student getStudent(@PathVariable Long id)
    {
        return studentService.getStudentById(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void studentNotFoundHandler(StudentNotFoundException e)
    {
    }

}
