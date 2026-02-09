package com.dms.service;

import com.dms.core.exceptions.EntityAlreadyExistsException;
import com.dms.core.exceptions.EntityInvalidArgumentException;
import com.dms.core.exceptions.EntityNotFoundException;
import com.dms.dto.ClientEditDTO;
import com.dms.dto.ClientInsertDTO;
import com.dms.dto.ClientReadOnlyDTO;
import com.dms.mapper.Mapper;
import com.dms.model.Client;
import com.dms.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService implements IClientService{

    private final ClientRepository clientRepository;
    private final Mapper mapper;

//    @Override
//    public List<String> clientList(String companyName) {
//        return List.of();
//    }

    @Override
    @Transactional
    public Page<ClientReadOnlyDTO> getPaginatedClients(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Client> clientPage = clientRepository.findAll(pageable);
        log.debug("Get paginated clients were returned successfully with page={} and size={}", page, size);
        return clientPage.map(mapper:: mapToClientReadOnlyDTO);
    }

    @Override
    @Transactional(rollbackOn = {EntityInvalidArgumentException.class, EntityAlreadyExistsException.class})
    public Client saveClient(ClientInsertDTO cdto) throws EntityAlreadyExistsException, EntityNotFoundException {

        try {
            if (cdto.getCompanyName() != null && clientRepository.findByCompanyName(cdto.getCompanyName()).isPresent()){
                throw new EntityAlreadyExistsException("Client", "with company name" + cdto.getCompanyName() + " already exists");
            }

            Client client = mapper.mapToClientEntity(cdto);
            clientRepository.save(client);
            log.info("Client with name={} got saved.", cdto.getCompanyName());
            return client;
        } catch (EntityAlreadyExistsException e) {
            log.error("Update failed for client with vat={}. Entity already exists.", cdto.getCompanyName(), e);
            throw e;
        }

    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateClient(ClientEditDTO cdto) throws EntityAlreadyExistsException, EntityInvalidArgumentException, EntityNotFoundException {

        try {

            Client client =  clientRepository.findByCid(cdto.getCid())
                    .orElseThrow(() -> new EntityNotFoundException("Client", "Client not found"));

            if (!client.getCompanyName().equals(cdto.getCompanyName())){
                if (clientRepository.findByCid(cdto.getCid()).isEmpty()) {
                    client.setCid(cdto.getCid());
                }
            }

            client.setCompanyName(cdto.getCompanyName());
            client.setCity(cdto.getCity());
            client.setContactPerson(cdto.getContactPerson());
            client.setVatNumber(cdto.getVatNumber());
            client.setRegistrationNumber(cdto.getRegistrationNumber());
            client.setEmail(cdto.getEmail());
            client.setPhone(cdto.getPhone());
            client.setWebsite(cdto.getWebsite());
            client.setAddressLine1(cdto.getAddressLine1());
            client.setAddressLine2(cdto.getAddressLine2());
            client.setStateProvince(cdto.getStateProvince());
            client.setPostalCode(cdto.getPostalCode());
            client.setCountry(cdto.getCountry());
            client.setCountryRegion(cdto.getCountryRegion());
            client.setIndustry(cdto.getIndustry());
            client.setBillingCurrency(cdto.getBillingCurrency());

            /* If ever needed to connect a repository to a different repo corresponding to an entity( and thus related to a table) */

//            if (!Objects.equals(client.getRegion().getId(), dto.getRegionId())) {
//                Region region = regionRepository.findById(dto.getRegionId())
//                        .orElseThrow(() -> new EntityInvalidArgumentException("Region", "Invalid region id"));
//                Region currentRegion = client.getRegion();
//                if (currentRegion != null) {
//                    currentRegion.removeclient(client);   // TBD
//                }
//                region.addclient(client);
//            }
            
            clientRepository.save(client);
            log.info("Client ={} got updated.", cdto.getCompanyName());
        } catch (EntityNotFoundException e) {
            log.error("Update failed for client with name={}. Entity not found.", cdto.getCompanyName(), e);
            throw e;
//        } catch (EntityAlreadyExistsException e) {
//            log.error("Update failed for client with vat={}. Entity already exists.", cdto.getCompanyName(), e);
//            throw e;
        }
    }

    // Αν υπάρχει, κάνε delete με το cid
    // Εναλλακτικά για soft delete χρειαζόμαστε πεδίο deleted (Boolean) και deletedAt (LocalDateTime)
    // Για soft delete κάνουμε setDeleted(true) και save

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteClientByCid(String cid) throws EntityNotFoundException {
        try {

            Client client = clientRepository.findByCid(cid)
                    .orElseThrow(() -> new EntityNotFoundException("Client", "Client with cid" + cid + " was not found"));
            clientRepository.deleteById(client.getId());

        } catch (EntityNotFoundException e) {
            log.error("Delete failed for client with cid={}. Client was not found,", cid, e);
            throw e;
        }
    }

//    @Override
//    @Transactional(rollbackOn = Exception.class)
//    public List<Client> returnClientList(){
//
//        List<Client> clientList = clientRepository.clientList();
//        return clientList;
//    }

}
