package com.dms.dto;
import com.dms.model.Task;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientInsertDTO {

    /* ================= Identification ================= */

    private String companyName;           // Company or individual name
    private String contactPerson;        // Main contact person

    //Variable across countries, leave null if vat cannot be found
    private String vatNumber;             // VAT / Tax ID

    //Have seen it with my own eyes, in foreign countries finding a registration number is difficult or time-consuming (ESPECIALLY CYPRUS), so it can be null until you find it
    private String registrationNumber;
    @NotNull(message = "email is required")
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "Invalid email")
    private String email;

    /* ================= Optional can be null details ================= */
    /* ================= Contact ================= */

    @Size(min=1, max=10)
    private String phone;

    @Pattern(regexp="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "Invalid URL")
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

    private Set<Task> tasks;

}
