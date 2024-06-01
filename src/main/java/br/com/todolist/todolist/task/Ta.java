package br.com.todolist.todolist.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class Ta {
   
    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/creating")
    public TaskModel create(@RequestBody TaskModel taskModel) {
       var task =  this.taskRepository.save(taskModel);

       return task;
    }
}
