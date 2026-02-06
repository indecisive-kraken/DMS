package com.dms.service;

import com.dms.core.exceptions.EntityAlreadyExistsException;
import com.dms.core.exceptions.EntityNotFoundException;
import com.dms.dto.UserInsertDTO;
import com.dms.dto.UserReadOnlyDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;

public interface IUserService {
    void saveUser(UserInsertDTO userInsertDTO) throws EntityAlreadyExistsException;

    @Transactional(rollbackOn = Exception.class)
    void deleteUserByUUID(String uuid) throws EntityNotFoundException;

    Page<UserReadOnlyDTO> getPaginatedUsers(int page, int size);
}
