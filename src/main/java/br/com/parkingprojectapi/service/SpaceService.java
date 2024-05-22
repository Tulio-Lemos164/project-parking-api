package br.com.parkingprojectapi.service;

import br.com.parkingprojectapi.entity.Space;
import br.com.parkingprojectapi.repository.SpaceRepository;
import br.com.parkingprojectapi.service.exceptions.CodeUniqueViolationException;
import br.com.parkingprojectapi.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.parkingprojectapi.entity.enums.SpaceStatus.FREE;

@Service
public class SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    @Transactional
    public Space insert(Space obj){
        try {
            return spaceRepository.save(obj);
        }
        catch (DataIntegrityViolationException e){
            throw new CodeUniqueViolationException(String.format("Code {%s} already in use", obj.getCode()));
        }
    }

    @Transactional(readOnly = true)
    public Space findByCode(String code){
        return spaceRepository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException("Resource not found. Code " + code));
    }

    @Transactional
    public Space findFreeSpace() {
        return spaceRepository.findFirstByStatus(FREE).orElseThrow(() -> new ResourceNotFoundException("No empty space was found!"));
    }
}
