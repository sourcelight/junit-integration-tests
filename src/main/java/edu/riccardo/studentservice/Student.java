package edu.riccardo.studentservice;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Column;
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
    @Column(nullable = false)
    private String name;
    private boolean active;
    private int grade;

    public Student(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }
}
