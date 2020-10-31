package com.info5059.exercises.employee;

import com.info5059.exercises.expense.Expense;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String title;
    private String firstname;
    private String lastname;
    private String phoneno;
    private String email;
    @OneToMany(mappedBy = "employeeid", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Expense> expenses = new HashSet<>();

}

