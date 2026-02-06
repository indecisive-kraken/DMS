package com.dms.service;

import com.dms.core.exceptions.EntityAlreadyExistsException;
import com.dms.core.exceptions.EntityNotFoundException;
import com.dms.dto.ClientReadOnlyDTO;
import com.dms.dto.UserInsertDTO;
import com.dms.dto.UserReadOnlyDTO;
import com.dms.mapper.Mapper;
import com.dms.model.Client;
import com.dms.model.User;
import com.dms.model.auth.Role;
import com.dms.repository.RoleRepository;
import com.dms.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Mapper mapper;
    private final RoleRepository roleRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void saveUser(UserInsertDTO userInsertDTO) throws EntityAlreadyExistsException {
        try {

            if (userRepository.findByUsername(userInsertDTO.getUuid()).isPresent()) {
                throw new EntityAlreadyExistsException("User",
                        "User with uuid: " + userInsertDTO.getUuid() + " already exists.");
            }
            User user = mapper.mapToUserEntity(userInsertDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

//            Role role = roleRepository.findByName("USER")
//                    .orElseThrow(() -> new RuntimeException("Default Role is not found"));
//            userRepository.save(user);

            Role role = roleRepository.findById(userInsertDTO.getRoleId()).orElse(null);
            user.setRole(role);
            userRepository.save(user);
            log.info("Save succeeded for user with username={}", userInsertDTO.getUsername());
        } catch (EntityAlreadyExistsException e) {
            log.error("Save failed for user with username={}. User already exists.", userInsertDTO.getUsername(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public Page<UserReadOnlyDTO> getPaginatedUsers(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);
        log.debug("Get paginated clients were returned successfully with page={} and size={}", page, size);
        return usersPage.map(mapper:: mapToUserReadOnlyDTO);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void deleteUserByUUID(String uuid) throws EntityNotFoundException {
        try {
            User user = userRepository.findByUuid(uuid);
//                    .orElseThrow(() -> new EntityNotFoundException("User", "with uuid: " + uuid + " not found"));

            userRepository.deleteById(user.getId());

            log.info("task with uuid={} deleted.", uuid);
        } catch (EntityNotFoundException e) {
            log.error("Delete failed for task with uuid={}. task not found.", uuid, e);

            // Rethrow, automatic rollback due to @Transactional
            throw e;
        }
    }

}
