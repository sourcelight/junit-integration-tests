package edu.riccardo.studentservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student
{
    @Id @GeneratedValue
    private Long id;
    private String name;
    private boolean active;
    private int grade;

    public Student(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }
}
