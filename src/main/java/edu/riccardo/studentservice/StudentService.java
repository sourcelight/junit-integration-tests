package edu.riccardo.studentservice;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService
{
    private final StudentRepository studentRepository;

    @Cacheable("students")
    public Student getStudentById(Long id)
    {
        return studentRepository
                .findById(id)
                .orElseThrow(() -> new StudentNotFoundException());
    }
}
