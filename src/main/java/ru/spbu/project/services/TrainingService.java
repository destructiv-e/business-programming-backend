package ru.spbu.project.services;

import java.time.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.spbu.project.models.Employee;
import ru.spbu.project.models.dto.ProductionPracticeDTO;
import ru.spbu.project.models.dto.TestDTO;
import ru.spbu.project.models.dto.TrainingApplicationDTO;
import ru.spbu.project.models.enums.Stage;
import ru.spbu.project.models.exceptions.DifferentStageException;
import ru.spbu.project.models.exceptions.TestTypeException;
import ru.spbu.project.models.exceptions.TimeUpException;

public interface TrainingService {

  long applyForTraining(TrainingApplicationDTO applicationDTO);

  void confirmTraining(Long employeeId, LocalDate date)
      throws TimeUpException, DifferentStageException;

  void refuseTraining(Long employeeId, String reason, LocalDate date)
      throws DifferentStageException, TimeUpException;

  boolean takeEntryTest(Long employeeId, TestDTO testDTO)
      throws TimeUpException, DifferentStageException, TestTypeException;

  boolean takeModuleTest(Long employeeId, TestDTO moduleTest)
      throws TimeUpException, DifferentStageException, TestTypeException;

  boolean takePracticeTask(Long employeeId, TestDTO practiceTask)
      throws TimeUpException, DifferentStageException, TestTypeException;

  void passingProductionPractice(ProductionPracticeDTO productionPracticeDTO)
      throws TimeUpException, DifferentStageException;

  boolean takeExam(Long employeeId, Boolean result) throws TimeUpException, DifferentStageException;

  HashMap<Stage, Integer> getFromPeriod(LocalDate startTime, LocalDate endTime);

  boolean productionPracticeResult(Long employeeId, Boolean result)
      throws IllegalArgumentException, TimeUpException, DifferentStageException;
}
