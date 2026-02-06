package com.dms.service;

import com.dms.core.exceptions.EntityAlreadyExistsException;
import com.dms.core.exceptions.EntityInvalidArgumentException;
import com.dms.core.exceptions.EntityNotFoundException;
import com.dms.dto.ClientEditDTO;
import com.dms.dto.ClientInsertDTO;
import com.dms.dto.ClientReadOnlyDTO;
import com.dms.model.Client;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IClientService {

//    @Transactional
//    List<String> clientList(String companyName);

    Page<ClientReadOnlyDTO> getPaginatedClients(int page, int size);

    Client saveClient(ClientInsertDTO clientInsertDTO) throws EntityAlreadyExistsException, EntityNotFoundException;

    void updateClient(ClientEditDTO clientEditDTO)
        throws EntityAlreadyExistsException, EntityInvalidArgumentException, EntityNotFoundException;

    void deleteClientByCid(String cid) throws EntityNotFoundException;


//    List<Client> returnClientList();
}
