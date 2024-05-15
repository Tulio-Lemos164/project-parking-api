package br.com.parkingprojectapi.service;

import br.com.parkingprojectapi.entity.User;
import br.com.parkingprojectapi.repository.UserRepository;
import br.com.parkingprojectapi.service.exceptions.DifferentPasswordsException;
import br.com.parkingprojectapi.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User insert(User obj) {
        return userRepository.save(obj);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public User updatePassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)){
            throw new DifferentPasswordsException("The new password is different from the confirmation camp password");
        }

        User user = findById(id);
        if (!user.getPassword().equals(currentPassword)){
            throw new DifferentPasswordsException("Your password does not match the password of the required user");
        }

        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
