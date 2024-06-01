package br.com.todolist.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserRepository userRepository;

    @RequestMapping("/")
    @PostMapping
    public UserModel create(@RequestBody UserModel UserModel) {
        var userCreated = this.userRepository.save(UserModel);
        return userCreated;
    }
}
