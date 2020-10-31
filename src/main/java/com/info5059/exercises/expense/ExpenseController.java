package com.info5059.exercises.expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
public class ExpenseController {
    @Autowired
    private ExpenseRepository expenseRepository;
    @GetMapping("/api/expenses")
    public ResponseEntity<Iterable<Expense>> findAll() {
        Iterable<Expense> expenses = expenseRepository.findAll();
        return new ResponseEntity<Iterable<Expense>>(expenses, HttpStatus.OK);
    }
    @PutMapping("/api/expenses")
    public ResponseEntity<Expense> updateOne(@RequestBody Expense expense) {
        Expense updatedExpense = expenseRepository.save(expense);
        return new ResponseEntity<Expense>(updatedExpense, HttpStatus.OK);
    }
    @PostMapping("/api/expenses")
    public ResponseEntity<Expense> addOne(@RequestBody Expense expense) {
        Expense newExpense = expenseRepository.save(expense);
        return new ResponseEntity<Expense>(newExpense, HttpStatus.OK);
    }
    @DeleteMapping("/api/expenses/{id}")
    public ResponseEntity<Integer> deleteOne(@PathVariable long id) {
        return new ResponseEntity<Integer>(expenseRepository.deleteOne(id), HttpStatus.OK);
    }
}
