package br.com.parkingprojectapi.service;

import br.com.parkingprojectapi.entity.User;
import br.com.parkingprojectapi.repository.UserRepository;
import br.com.parkingprojectapi.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
