package com.dms.validator;

import com.dms.dto.ClientInsertDTO;
import com.dms.dto.TaskInsertDTO;
import com.dms.repository.ClientRepository;
import com.dms.repository.TaskRepository;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
@RequiredArgsConstructor
@Slf4j
public class ClientInsertValidator implements Validator {

    private final ClientRepository clientRepository;

    @Override
    public boolean supports(@NonNull Class<?> clazz) { return TaskInsertDTO.class == clazz; }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {

        ClientInsertDTO clientInsertDTO = (ClientInsertDTO) target;

        //I know here there are subtle edge cases for upper case and lower case with names, I will leave it loose, until I define a more nuanced approach
        //because, if I use .lowerCase(), I restrict the options of the user and somebody might want to write the company with lowercase to denote a related record with the og record.

        if (clientInsertDTO.getCompanyName() != null && clientRepository.findByCompanyName(clientInsertDTO.getCompanyName()).isPresent()) {
            log.error("Save failed for company with name:{}. Record already exists.", clientInsertDTO.getCompanyName());
            errors.rejectValue("company","company.already.exists");
        }

    }
}
