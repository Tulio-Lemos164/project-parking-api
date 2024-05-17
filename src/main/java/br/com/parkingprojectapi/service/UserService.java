package br.com.parkingprojectapi.service;

import br.com.parkingprojectapi.entity.User;
import br.com.parkingprojectapi.entity.enums.Role;
import br.com.parkingprojectapi.repository.UserRepository;
import br.com.parkingprojectapi.service.exceptions.DifferentPasswordsException;
import br.com.parkingprojectapi.service.exceptions.ResourceNotFoundException;
import br.com.parkingprojectapi.service.exceptions.UsernameUniqueViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User insert(User obj) {
        try {
            obj.setPassword(passwordEncoder.encode(obj.getPassword()));
            return userRepository.save(obj);
        }
        catch (DataIntegrityViolationException e){
            throw new UsernameUniqueViolationException(String.format("Username {%s} already in use", obj.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException("Resource not found. Username " + id));
    }

    @Transactional
    public User updatePassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)){
            throw new DifferentPasswordsException("The new password is different from the confirmation camp password");
        }

        User user = findById(id);
        if (!passwordEncoder.matches(currentPassword, user.getPassword())){
            throw new DifferentPasswordsException("Your password does not match the password of the required user");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Resource not found. Username " + username));
    }

    public Role findRoleByUsername(String username) {
        return userRepository.finrRoleByUsername(username);
    }
}
