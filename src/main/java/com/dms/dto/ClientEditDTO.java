package com.dms.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ClientEditDTO {

    private String companyName;
    private Long id;

    @NotNull
    private String cid;
    /* ================= Identification ================= */

            // Company or individual name
    private String contactPerson;        // Main contact person

    private String vatNumber;             // VAT / Tax ID
    private String registrationNumber;   // Company registration number

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



}
