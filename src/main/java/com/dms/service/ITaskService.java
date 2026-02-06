package com.dms.service;

import com.dms.core.exceptions.EntityAlreadyExistsException;
import com.dms.core.exceptions.EntityInvalidArgumentException;
import com.dms.core.exceptions.EntityNotFoundException;
import com.dms.dto.TaskEditDTO;
import com.dms.dto.TaskReadOnlyDTO;
import com.dms.dto.TaskInsertDTO;
import com.dms.model.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;

public interface ITaskService {


    Task saveTask(TaskInsertDTO taskInsertDTO)
            throws EntityAlreadyExistsException, EntityInvalidArgumentException;

    Page<TaskReadOnlyDTO> getPaginatedTasks(int page, int size);

    void updateTask(TaskEditDTO dto)
            throws EntityAlreadyExistsException, EntityInvalidArgumentException, EntityNotFoundException;

    void deleteTaskByUUID(String uuid) throws EntityNotFoundException;

    void findTaskByUUID(String uuid) throws EntityNotFoundException;

}
