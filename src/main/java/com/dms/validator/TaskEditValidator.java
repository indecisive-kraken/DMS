package com.dms.validator;

import com.dms.dto.TaskEditDTO;
import com.dms.model.Task;
import com.dms.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Slf4j
@RequiredArgsConstructor
public class TaskEditValidator implements Validator {
    private final TaskRepository taskRepository;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return TaskEditDTO.class == clazz;
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        TaskEditDTO taskEditDTO = (TaskEditDTO) target;

        Task task = taskRepository.findByUuid(taskEditDTO.getUuid())
                .orElse(null);
        if (task != null && !task.getNoteTitle().equals(taskEditDTO.getNoteTitle())) {

            if (taskRepository.findByNoteTitle(taskEditDTO.getNoteTitle()).isPresent()) {
                log.error("Save failed for task with ={}. task already exists", taskEditDTO.getCid());
                errors.rejectValue("vat", "vat.task.exists");
            }
        }
    }
}
