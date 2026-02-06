package com.dms.repository;

//import com.dms.core.enums.Role;
import com.dms.core.exceptions.EntityNotFoundException;
import com.dms.model.User;
import com.dms.model.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByRole(Role role);
    Optional<User> findByUsername(String username);
    User findByUuid(String uuid) throws EntityNotFoundException;
}
