package br.com.todolist.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "db_task")
public class TaskModel {
    private UUID idUser;

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String description;
    
    @Column(length = 50)
    private String title;
    private String priority;

    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

}
