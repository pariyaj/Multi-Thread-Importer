package com.priyajafari.multithreadproject.repository;

import com.priyajafari.multithreadproject.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("SELECT c FROM Customer c WHERE c.customerId IN :customerIds")
    Customer findByCustomerId(@Param("customerIds") Long customerId);
}
