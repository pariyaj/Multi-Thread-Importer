package com.priyajafari.multithreadproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "customer_id",nullable = false,unique = true)
    private Integer customerId;

    @NotNull
    @Column(name = "record_number", nullable = false)
    private int recordNumber;

    @NotNull
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @NotNull
    @Column(name = "customer_surname", nullable = false)
    private String customerSurname;

    @NotNull
    @Column(name = "customer_address")
    private String customerAddress;

    @NotNull
    @Column(name = "customer_zip_code")
    private String customerZipCode;

    @NotNull
    @Column(name = "customer_national_id", length = 10, nullable = false)
    private String customerNationalId;
    @NotNull
    @Column(name = "customer_birth_date", nullable = false)
    private Date customerBirthDate;

    public @NotNull Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(@NotNull Integer customerId) {
        this.customerId = customerId;
    }

    @NotNull
    public int getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(@NotNull int recordNumber) {
        this.recordNumber = recordNumber;
    }

    public @NotNull String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(@NotNull String customerName) {
        this.customerName = customerName;
    }

    public @NotNull String getCustomerSurname() {
        return customerSurname;
    }

    public void setCustomerSurname(@NotNull String customerSurname) {
        this.customerSurname = customerSurname;
    }

    public @NotNull String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(@NotNull String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public @NotNull String getCustomerZipCode() {
        return customerZipCode;
    }

    public void setCustomerZipCode(@NotNull String customerZipCode) {
        this.customerZipCode = customerZipCode;
    }

    public @NotNull String getCustomerNationalId() {
        return customerNationalId;
    }

    public void setCustomerNationalId(@NotNull String customerNationalId) {
        this.customerNationalId = customerNationalId;
    }

    public @NotNull Date getCustomerBirthDate() {
        return customerBirthDate;
    }

    public void setCustomerBirthDate(@NotNull Date customerBirthDate) {
        this.customerBirthDate = customerBirthDate;
    }
}
