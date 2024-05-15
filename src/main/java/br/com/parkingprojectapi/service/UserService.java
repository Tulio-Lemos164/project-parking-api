package br.com.parkingprojectapi.service;

import br.com.parkingprojectapi.entity.User;
import br.com.parkingprojectapi.repository.UserRepository;
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
    public User updatePassword(Long id, String password) {
        User user = findById(id);
        user.setPassword(password);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
