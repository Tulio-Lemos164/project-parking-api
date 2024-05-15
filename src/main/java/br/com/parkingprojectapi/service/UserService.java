package br.com.parkingprojectapi.service;

import br.com.parkingprojectapi.entity.User;
import br.com.parkingprojectapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User insert(User obj) {
        return userRepository.save(obj);
    }
}
