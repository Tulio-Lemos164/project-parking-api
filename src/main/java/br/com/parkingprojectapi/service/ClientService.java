package br.com.parkingprojectapi.service;

import br.com.parkingprojectapi.entity.Client;
import br.com.parkingprojectapi.repository.ClientRepository;
import br.com.parkingprojectapi.repository.projection.ClientProjection;
import br.com.parkingprojectapi.service.exceptions.CpfUniqueViolationException;
import br.com.parkingprojectapi.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public Client insert(Client obj){
        try {
            return clientRepository.save(obj);
        }
        catch (DataIntegrityViolationException e){
            throw new CpfUniqueViolationException(String.format("CPF %s can't be registered. It already exits in the system", obj.getCpf()));
        }
    }

    @Transactional(readOnly = true)
    public Client findById(Long id){
        Optional<Client> obj = clientRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException("Resource not found. id " + id));
    }

    @Transactional(readOnly = true)
    public Page<ClientProjection> findAll(Pageable pageable){
        return clientRepository.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    public Client findDetailsFromUserId(Long id) {
        return clientRepository.findByUserId(id);
    }

    @Transactional(readOnly = true)
    public Client findByCpf(String cpf) {
        return clientRepository.findByCpf(cpf).orElseThrow(() -> new ResourceNotFoundException("Resource not found. cpf " + cpf));
    }
}
