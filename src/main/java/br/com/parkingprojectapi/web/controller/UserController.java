package br.com.parkingprojectapi.web.controller;

import br.com.parkingprojectapi.entity.User;
import br.com.parkingprojectapi.service.UserService;
import br.com.parkingprojectapi.web.dto.UserInsertDTO;
import br.com.parkingprojectapi.web.dto.UserResponseDTO;
import br.com.parkingprojectapi.web.dto.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> insert(@RequestBody UserInsertDTO obj){
        User user = userService.insert(UserMapper.toUser(obj));
        return ResponseEntity.status(201).body(UserMapper.toDTO(user));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok().body(UserMapper.toDTO(user));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User obj){
        User user = userService.updatePassword(id, obj.getPassword());
        return ResponseEntity.ok().body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }
}
