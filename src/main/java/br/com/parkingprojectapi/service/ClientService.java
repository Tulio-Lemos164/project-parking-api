package br.com.parkingprojectapi.service;

import br.com.parkingprojectapi.entity.Client;
import br.com.parkingprojectapi.repository.ClientRepository;
import br.com.parkingprojectapi.service.exceptions.CpfUniqueViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client insert(Client obj){
        try {
            return clientRepository.save(obj);
        }
        catch (DataIntegrityViolationException e){
            throw new CpfUniqueViolationException(String.format("CPF %s can't be registered. It already exits in the system", obj.getCpf()));
        }
    }
}
