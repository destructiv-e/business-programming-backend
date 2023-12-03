package ru.spbu.project.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spbu.project.models.Employee;
import ru.spbu.project.models.Leader;
import ru.spbu.project.models.dto.UpdateEmployeeDTO;
import ru.spbu.project.models.enums.Stage;
import ru.spbu.project.repositories.EmployeeRepository;
import ru.spbu.project.repositories.LeaderRepository;
import ru.spbu.project.services.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

  private final EmployeeRepository employeeRepository;
  private final EmployeeService employeeService;
  private final LeaderRepository leaderRepository;

  public EmployeeController(EmployeeRepository employeeRepository, EmployeeService employeeService, LeaderRepository leaderRepository) {
    this.employeeRepository = employeeRepository;
    this.employeeService = employeeService;
    this.leaderRepository = leaderRepository;
  }

  @GetMapping("")
  public ResponseEntity<List<Employee>> getParticipants(
          @RequestParam(required = false) List<Stage> stages,
          @RequestParam(required = false) String search) {
    List<Employee> employeeList;

    boolean stagesNotEmpty = (stages != null && !stages.isEmpty());
    boolean searchNotEmpty = (search != null);

    if (stagesNotEmpty && searchNotEmpty) {
      employeeList = new ArrayList<>();
      for (Stage stage: stages) {
        employeeList.addAll(employeeRepository.findByStageAndName(search, stage));
      }
    } else if (searchNotEmpty) {
      employeeList = employeeRepository.searchByName(search);
    } else if (stagesNotEmpty) {
      employeeList = new ArrayList<>();
      for (Stage stage: stages) {
        employeeList.addAll(employeeRepository.findByStage(stage));
      }
    } else {
      employeeList = employeeRepository.findAll();
    }
    return new ResponseEntity<>(employeeList, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> changeEmployee(@PathVariable Long id, @RequestBody UpdateEmployeeDTO updateInfo) {
    Employee employee = employeeService.findEmployeeByID(id);
    Leader leader = leaderRepository.findById(updateInfo.getLeader()).orElseThrow(() -> new IllegalArgumentException("There is no leader with id: " + updateInfo.getLeader()));
    employee.setName(updateInfo.getName());
    employee.setSurname(updateInfo.getSurname());
    employee.setPatronymic(updateInfo.getPatronymic());
    employee.setJobTitle(updateInfo.getJob());
    employee.setProject(updateInfo.getProject());
    employee.setTrainingPurpose(updateInfo.getPurpose());
    employee.setStage(updateInfo.getStage());
    employee.setLeader(leader);
    employee.setStartTime(updateInfo.getStart());
    employee.setReasonForRefuseTraining(updateInfo.getReason());
    employee.setActive(updateInfo.isActive());
    employeeRepository.save(employee);
    return new ResponseEntity<>("Employee info successfully changed", HttpStatus.valueOf(204));
  }

  @GetMapping("/find-employee-by-id/{employeeId}")
  public ResponseEntity<Employee> findEmployeeByID(@PathVariable Long employeeId) {
    Employee employee = employeeService.findEmployeeByID(employeeId);
    return new ResponseEntity<>(employee, HttpStatus.OK);
  }
}

