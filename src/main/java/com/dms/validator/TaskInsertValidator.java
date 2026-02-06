package com.dms.validator;

import com.dms.dto.TaskInsertDTO;
import com.dms.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskInsertValidator implements Validator {
    private final TaskRepository taskRepository;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return TaskInsertDTO.class == clazz;
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        TaskInsertDTO taskInsertDTO = (TaskInsertDTO) target;

        if (taskInsertDTO.getNoteTitle() != null && taskRepository.findByUuid(taskInsertDTO.getNoteTitle()).isPresent()) {
            log.error("Save failed for task  with note title={}. task already exists", taskInsertDTO.getNoteTitle());
//            errors.rejectValue("vat", "vat.task.exists", "Το ΑΦΜ του καθηγητή υπάρχει ήδη.");
            errors.rejectValue("notetitle", "notetitle.task.exists");
        }

//        if (taskInsertDTO.getRegionId() != null && regionRepository.findById(taskInsertDTO.getRegionId()).isEmpty()) {
//            log.error("Save failed for task with vat={}. Region id={} invalid.",
//                    taskInsertDTO.getVat(), taskInsertDTO.getRegionId());
//            errors.rejectValue("regionId", "vat.task.exists", "Η περιοχή του Καθηγητή δεν μπορεί να είναι κενή.");
//        }
    }
}
