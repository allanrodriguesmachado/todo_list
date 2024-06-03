package br.com.todolist.todolist.task;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {
   
    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/creating")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        var user_id = request.getAttribute("idUser");
        System.out.println(user_id);
        var currentDate = LocalDateTime.now();
        taskModel.setIdUser((UUID) user_id);

        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicial deve ser maior que a data atual");
        }

        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicial deve ser maior que a data atual");
        }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/list")
    public List<TaskModel> index(HttpServletRequest request) {
        var user_id = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID) user_id);

        return tasks;
    }
}
