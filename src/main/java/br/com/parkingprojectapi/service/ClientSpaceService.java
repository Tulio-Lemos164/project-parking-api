package br.com.parkingprojectapi.service;

import br.com.parkingprojectapi.entity.ClientSpace;
import br.com.parkingprojectapi.repository.ClientSpaceRepository;
import br.com.parkingprojectapi.service.exceptions.ResourceNotFoundException;
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

    @Transactional(readOnly = true)
    public ClientSpace findByReceipt(String receipt) {
        return clientSpaceRepository.findByReceiptAndExitDateIsNull(receipt)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Receipt '%s' not found or check-out was already made.", receipt)));
    }
}
