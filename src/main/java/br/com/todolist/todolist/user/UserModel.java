package br.com.todolist.todolist.user;

import java.util.UUID;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_users")
public class UserModel {
    @Id 
    @GeneratedValue(generator = "UUID")
    private UUID uuid;

    @Column(unique = true)
    private String username;
    private String name;

    @Column(length = 10)
    private String password;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
}
