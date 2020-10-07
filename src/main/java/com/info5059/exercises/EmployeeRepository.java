package com.info5059.exercises;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "employees", path = "employees")
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    // extend so we can return the number of rows deleted
    // modifying meaning going to change database && Transactional requires this for delete and update operations
    @Modifying
    @Transactional
    @Query("delete from Employee where id = ?1")
    int deleteOne(Long employeeid);

}
