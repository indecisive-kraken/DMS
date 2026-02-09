package com.dms.service;

import com.dms.core.exceptions.EntityAlreadyExistsException;
import com.dms.core.exceptions.EntityInvalidArgumentException;
import com.dms.core.exceptions.EntityNotFoundException;
import com.dms.dto.TaskEditDTO;
import com.dms.dto.TaskReadOnlyDTO;
import com.dms.dto.TaskInsertDTO;
import com.dms.mapper.Mapper;
import com.dms.model.Client;
import com.dms.model.Task;
import com.dms.repository.ClientRepository;
import com.dms.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;
    private final ClientRepository clientRepository;
    private final Mapper mapper;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Task saveTask(TaskInsertDTO taskInsertDTO)
            throws EntityAlreadyExistsException, EntityInvalidArgumentException {
        try {
            if (taskInsertDTO.getNoteTitle() != null && taskRepository.findByUuid(taskInsertDTO.getNoteTitle()).isPresent()) {
                throw new EntityAlreadyExistsException("Task", "Task with this title " + taskInsertDTO.getNoteTitle() + " already exists");
            }

            Client client = clientRepository.findById(taskInsertDTO.getCid())    // TDB check for null
                    .orElseThrow(() -> new EntityInvalidArgumentException("Client", "Invalid client id"));

            Task task = mapper.mapTotaskEntity(taskInsertDTO);

            client.addtask(task);
            taskRepository.save(task);
            task.checkIfTaskIsOverdue(task);

            log.info("task with title={} saved.", taskInsertDTO.getNoteTitle());   // structured logging vat={} parametrized placeholder design pattern
            return task;
        } catch (EntityAlreadyExistsException e) {
            log.error("Save failed for task  with Note Title={}. task already exists", taskInsertDTO.getNoteTitle(), e);
            throw e;
        }
    }


    @Override
    @Transactional(rollbackOn = Exception.class)
    public Page<TaskReadOnlyDTO> getPaginatedTasks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> taskPage = taskRepository.findAll(pageable);
        log.debug("Get paginated tasks were returned successfully with page={} and size={}", page, size);
        return taskPage.map(mapper::mapToTaskReadOnlyDTO);
    }


    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateTask(TaskEditDTO dto)
            throws EntityAlreadyExistsException, EntityInvalidArgumentException, EntityNotFoundException {
        try {
            Task task = taskRepository.findByUuid(dto.getUuid())
                    .orElseThrow(() -> new EntityNotFoundException("task", "task not found"));

            if (!task.getDescription().equals(dto.getDescription())) {
                if (taskRepository.findByDescription(dto.getDescription()).isEmpty()) {
                    task.setDescription(dto.getDescription());
                } else
                    throw new EntityAlreadyExistsException("Task", "Task with description" + dto.getDescription() + "..." + " already exists"); //I need to trim this will do later
            }

            task.setNoteTitle(dto.getNoteTitle());
            task.setDescription(dto.getDescription());
            task.checkIfTaskIsOverdue(task);

//            if (!Objects.equals(task.getClient().getId(), dto.getNoteTitle())) {
//
//                Client client = clientRepository.findById(dto.getId())
//                        .orElseThrow(() -> new EntityInvalidArgumentException("Client", "Invalid client id"));
//
//                Client currentClient = task.getClient();
//                if (currentClient != null) {
//                    currentClient.removetask(task);
//                }
//                client.addtask(task);
//            }
            taskRepository.save(task);
            log.info("task with id={} updated.", dto.getUuid());
        } catch (EntityNotFoundException e) {
            log.error("Update failed for task with vat={}. Entity not found.", dto.getUuid(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteTaskByUUID(String uuid) throws EntityNotFoundException {
        try {
            Task task = taskRepository.findByUuid(uuid)
                    .orElseThrow(() -> new EntityNotFoundException("task", "task with uuid: " + uuid + " not found"));

            taskRepository.deleteById(task.getId());

            log.info("task with uuid={} deleted.", uuid);
        } catch (EntityNotFoundException e) {
            log.error("Delete failed for task with uuid={}. task not found.", uuid, e);

            // Rethrow, automatic rollback due to @Transactional
            throw e;
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void findTaskByUUID(String uuid) throws EntityNotFoundException {
        try {
            taskRepository.findByUuid(uuid)
                    .orElseThrow(() -> new EntityNotFoundException("task", "task with uuid: " + uuid + " not found"));

        }catch (EntityNotFoundException e){
            log.error("Task with uuid={} was not found.", uuid, e);
            throw e;
        }
    }

}
