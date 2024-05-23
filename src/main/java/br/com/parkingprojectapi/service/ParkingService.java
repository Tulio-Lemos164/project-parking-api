package br.com.parkingprojectapi.service;

import br.com.parkingprojectapi.entity.Client;
import br.com.parkingprojectapi.entity.ClientSpace;
import br.com.parkingprojectapi.entity.Space;
import br.com.parkingprojectapi.entity.enums.SpaceStatus;
import br.com.parkingprojectapi.repository.projection.ClientSpaceProjection;
import br.com.parkingprojectapi.util.ParkingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Transactional(readOnly = true)
    public ClientSpace findByReceipt(String receipt) {
        return clientSpaceService.findByReceipt(receipt);
    }

    @Transactional
    public ClientSpace checkOut(String receipt) {
        ClientSpace clientSpace = clientSpaceService.findByReceipt(receipt);

        LocalDateTime exitDate = LocalDateTime.now();
        clientSpace.setExitDate(exitDate);

        BigDecimal paymentValue = ParkingUtils.calculateCost(clientSpace.getEntryDate(), exitDate);
        clientSpace.setValue(paymentValue);

        long numberOfTimes = clientSpaceService.findTotalTimesFullParking(clientSpace.getClient().getCpf());

        BigDecimal discount = ParkingUtils.calculateDiscount(paymentValue, numberOfTimes);
        clientSpace.setDiscount(discount);

        clientSpace.getSpace().setStatus(SpaceStatus.FREE);

        return clientSpaceService.insert(clientSpace);
    }

    @Transactional(readOnly = true)
    public Page<ClientSpaceProjection> findAllByClientCpf(String cpf, Pageable pageable) {
        return clientSpaceService.findAllByClientCpf(cpf, pageable);
    }

    @Transactional(readOnly = true)
    public Page<ClientSpaceProjection> findAllByUserId(Long id, Pageable pageable) {
        return clientSpaceService.findAllByUserId(id, pageable);
    }
}
