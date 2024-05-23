package br.com.parkingprojectapi.service;

import br.com.parkingprojectapi.entity.Client;
import br.com.parkingprojectapi.entity.ClientSpace;
import br.com.parkingprojectapi.entity.Space;
import br.com.parkingprojectapi.entity.enums.SpaceStatus;
import br.com.parkingprojectapi.util.ParkingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ParkingService {

    @Autowired
    private ClientSpaceService clientSpaceService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private SpaceService spaceService;

    @Transactional
    public ClientSpace checkIn(ClientSpace clientSpace){
        Client client = clientService.findByCpf(clientSpace.getClient().getCpf());
        clientSpace.setClient(client);

        Space space = spaceService.findFreeSpace();
        space.setStatus(SpaceStatus.OCCUPIED);
        clientSpace.setSpace(space);

        clientSpace.setEntryDate(LocalDateTime.now());

        clientSpace.setReceipt(ParkingUtils.generateReceipt());

        return clientSpaceService.insert(clientSpace);
    }

    public ClientSpace findByReceipt(String receipt) {
        return clientSpaceService.findByReceipt(receipt);
    }
}
