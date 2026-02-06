package com.dms.repository;

import com.dms.model.Client;
import com.dms.model.Task;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository
        extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    Optional<Task> findByUuid(String uuid);

    @Query("SELECT count(t) FROM Task t WHERE t.uuid = ?1")
    long getCount(String uuid);

    Optional<Object> findByNoteTitle(@NotNull @Size(min = 120) String noteTitle);

}
