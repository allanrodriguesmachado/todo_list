package br.com.todolist.todolist.task;

import br.com.todolist.todolist.utils.Utils;
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
    public List<TaskModel> index( HttpServletRequest request) {
        var user_id = request.getAttribute("idUser");
        return this.taskRepository.findByIdUser((UUID) user_id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
        var task = this.taskRepository.findById(id).orElse(null );
        var user_id = request.getAttribute("idUser");

//        if(task == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicial deve ser maior que a data atual");
//        }

//        if(!task.getIdUser().equals(user_id)) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicial deve ser maior que a data atual");
//        }

        Utils.copyNonNullProperties(taskModel, task);
        assert task != null;
        var taskUpdate = this.taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.OK).body(taskUpdate);
    }
}
