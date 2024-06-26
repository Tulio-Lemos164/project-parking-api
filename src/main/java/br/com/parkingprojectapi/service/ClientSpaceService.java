package br.com.parkingprojectapi.service;

import br.com.parkingprojectapi.entity.ClientSpace;
import br.com.parkingprojectapi.repository.ClientSpaceRepository;
import br.com.parkingprojectapi.repository.projection.ClientSpaceProjection;
import br.com.parkingprojectapi.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    public long findTotalTimesFullParking(String cpf) {
        return clientSpaceRepository.countByClientCpfAndExitDateIsNotNull(cpf);
    }

    @Transactional(readOnly = true)
    public Page<ClientSpaceProjection> findAllByClientCpf(String cpf, Pageable pageable) {
        return clientSpaceRepository.findAllByClientCpf(cpf, pageable);
    }

    @Transactional(readOnly = true)
    public Page<ClientSpaceProjection> findAllByUserId(Long id, Pageable pageable) {
        return clientSpaceRepository.findAllByClientUserId(id, pageable);
    }
}
