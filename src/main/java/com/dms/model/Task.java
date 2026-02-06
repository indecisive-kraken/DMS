package com.dms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tasks")
public class Task extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String uuid;

    @Column(length = 2000)
    private String description;

    @Column(length=120)
    private String noteTitle;

    //I am not very strict here, do not make this nullable = false, you want some arbitrary tasks to not have an end date
    @Column(unique = true)
    private LocalDateTime inDue;

    //mappedBy = "client",
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid")
    private Client client;

    @Column
    private String status;

    @PrePersist
    public void initializeUUID() {
        if (uuid == null) uuid = UUID.randomUUID().toString();
    }


    public void checkIfTaskIsOverdue(Task task) {

        LocalDateTime currentTime = LocalDateTime.now();
        String status = "";

        try {
            if (task.getInDue().isAfter(currentTime)) {
                task.setStatus("Pending");

            }else {
                task.setStatus("Overdue");
            }
        }catch (Exception e) {
            log.error("Something went wrong. Cound not get the time of creation of the tasks", e);
        }

    }

}
