package br.com.parkingprojectapi.service;

import br.com.parkingprojectapi.entity.ClientSpace;
import br.com.parkingprojectapi.repository.ClientSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientSpaceService {

    @Autowired
    private ClientSpaceRepository clientSpaceRepository;

    @Transactional
    public ClientSpace insert(ClientSpace clientSpace){
        return clientSpaceRepository.save(clientSpace);
    }
}
