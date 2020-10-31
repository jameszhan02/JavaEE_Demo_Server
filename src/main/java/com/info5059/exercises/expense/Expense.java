package com.info5059.exercises.expense;
        import lombok.Data;
        import lombok.RequiredArgsConstructor;
        import javax.persistence.*;
        import java.math.BigDecimal;
/**
 * Expense entity
 */
@Entity
@Data
@RequiredArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private long employeeid; // FK
    private String categoryid; // FK
    private String description;
    private boolean receipt;
    private BigDecimal amount;
    private String dateincurred;
    // needed in case 2
    @Basic(optional = true)
    @Lob
    private String receiptscan;
}
