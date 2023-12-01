package ru.spbu.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.spbu.project.models.Employee;
import ru.spbu.project.models.Test;
import ru.spbu.project.models.enums.TestType;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    @Query("select t from Test t where t.employee = ?1 and t.testType = ?2")
    List<Test> employeeFindTest(Employee employee, TestType testType);

    @Query("select e from Employee e where e.surname = ?1 and e.name = ?2 and e.patronymic = ?3")
    List<Employee> findByName(String surname, String name, String patronymic);
}
