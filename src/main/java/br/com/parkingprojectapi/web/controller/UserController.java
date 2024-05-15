package br.com.parkingprojectapi.web.controller;

import br.com.parkingprojectapi.entity.User;
import br.com.parkingprojectapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> insert(@RequestBody User obj){
        User user = userService.insert(obj);
        return ResponseEntity.status(201).body(user);
    }
}
