package com.dms.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Client extends AbstractEntity {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Column(unique = true)
     private String cid;
     /* ================= Identification ================= */

    @Column(nullable = false)
    private String companyName;          // Company or individual name
    private String contactPerson;        // Main contact person

    @Column(unique = true)
    private String vatNumber;             // VAT / Tax ID
    private String registrationNumber;    // Company registration number

    /* ================= Optional can be null details ================= */

    /* ================= Contact ================= */
    private String email;
    private String phone;
    private String website;

    /* ================= Address ================= */
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String stateProvince;
    private String postalCode;
    private String country;
    private String countryRegion;

    /* ================= Business Details ================= */
    private String industry;
    private String billingCurrency;


    /* ================= Status ================= */

    @Getter(AccessLevel.PRIVATE) //Check if this will cause problems
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<Task> tasks = new HashSet<>();

    public Set<Task> getAlltasks() {
        return Collections.unmodifiableSet(tasks);
    }

    public void addtask(Task task) {
        if (tasks == null) tasks = new HashSet<>();
            tasks.add(task);
            task.setClient(this);
        }

        public void removetask(Task task) {
            if (tasks == null) return;
             this.tasks.remove(task);
             task.setClient(null);
        }

    @PrePersist
    public void initializeCID() {
        if (cid == null) cid = UUID.randomUUID().toString();
    }
}
