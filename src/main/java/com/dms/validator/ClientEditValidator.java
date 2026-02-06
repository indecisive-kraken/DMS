package com.dms.validator;

import com.dms.dto.ClientEditDTO;
import com.dms.model.Client;
import com.dms.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClientEditValidator implements Validator {

    private final ClientRepository clientRepository;

    @Override
    public boolean supports(Class<?> clazz) { return ClientEditDTO.class == clazz; }

    @Override
    public void validate(Object target, Errors errors) {
        ClientEditDTO clientEditDTO = (ClientEditDTO) target;

        Client client = clientRepository.findByCid(clientEditDTO.getCid())
                .orElse(null);

        if (client != null && !client.getCompanyName().equals(clientEditDTO.getCompanyName())){
            if (clientRepository.findByCompanyName(clientEditDTO.getCompanyName()).isPresent()){
                log.error("Save failed for company with company name={}. Company with this name already exists", clientEditDTO.getCompanyName());
                errors.rejectValue("companyName", "companyName.exists");
            }
        }
    }
}
