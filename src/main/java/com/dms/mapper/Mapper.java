package com.dms.mapper;

//import com.dms.core.enums.Role;
import com.dms.dto.*;
//import com.dms.model.AIUrl;
import com.dms.model.Client;
import com.dms.model.Task;
import com.dms.model.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Task mapTotaskEntity(TaskInsertDTO dto) {
        return new Task(null, null, dto.getNoteTitle(), dto.getDescription(), dto.getInDue(), null, null);
    }

    public TaskReadOnlyDTO mapToTaskReadOnlyDTO(Task task) {
        return new TaskReadOnlyDTO(task.getId(), task.getCreatedAt(), task.getUpdatedAt(), task.getUuid(),
                task.getNoteTitle(), task.getDescription(), task.getStatus(), task.getClient().getCompanyName(),task.getInDue());
    }

    public TaskEditDTO mapToTaskEditDTO(Task task) {
        return new TaskEditDTO(task.getId(), task.getUuid(), task.getNoteTitle(),
                task.getDescription(), task.getClient().getId(), task.getClient().getCompanyName(), task.getStatus(), task.getInDue());
    }

    public Client mapToClientEntity(ClientInsertDTO client) {
        return new Client(null, null, client.getCompanyName(), client.getContactPerson(), client.getVatNumber(),
                client.getRegistrationNumber(), client.getEmail(), client.getPhone(), client.getWebsite(),
                client.getAddressLine1(), client.getAddressLine2(),
                client.getCity(), client.getStateProvince(), client.getPostalCode(),
                client.getCountry(), client.getCountryRegion(), client.getIndustry(), client.getBillingCurrency(), null);
    }

    public ClientEditDTO mapToClientEditDTO(Client client) {
        return new ClientEditDTO(client.getCompanyName(),client.getId(), client.getCid(),
                client.getContactPerson(),
                client.getVatNumber(), client.getRegistrationNumber(),
                client.getEmail(), client.getPhone(), client.getWebsite(),
                client.getAddressLine1(), client.getAddressLine2(), client.getCity(),
                client.getStateProvince(), client.getPostalCode(), client.getCountry(),
                client.getCountryRegion(), client.getIndustry(), client.getBillingCurrency());
    }

    public ClientReadOnlyDTO mapToClientReadOnlyDTO(Client client){
        return new ClientReadOnlyDTO(client.getCompanyName(),client.getId(), client.getCid(),
                client.getContactPerson(),
                client.getVatNumber(), client.getRegistrationNumber(),
                client.getEmail(), client.getPhone(), client.getWebsite(),
                client.getAddressLine1(), client.getAddressLine2(), client.getCity(),
                client.getStateProvince(), client.getPostalCode(), client.getCountry(),
                client.getCountryRegion(), client.getIndustry(), client.getBillingCurrency());
    }

    public User mapToUserEntity(UserInsertDTO userInsertDTO) {
//        return new User(userInsertDTO.getUsername(), userInsertDTO.getPassword(),
//                Role.valueOf(userInsertDTO.getRole().toUpperCase()));

        return User.builder()
                .username(userInsertDTO.getUsername())
                .password(userInsertDTO.getPassword())
//                .role(Role.valueOf(userInsertDTO.getRole().toUpperCase()))
                .build();
    }

    public UserReadOnlyDTO mapToUserReadOnlyDTO(User user){
        return new UserReadOnlyDTO(
         user.getCreatedAt(), user.getUpdatedAt(),
                null,  user.getUuid(),
                user.getEmail(), user.getUsername(),
                null, user.getRole()
        );
    }

//    public AILinkReadOnlyDTO mapToAILinkReadOnlyDTO(AIUrl aiUrl){
//        return new AILinkReadOnlyDTO(
//                aiUrl.getAi_sid_u(), aiUrl.getAi_url()
//        );
//    }
}
