package com.priyajafari.multithreadproject.repository;

import com.priyajafari.multithreadproject.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {

}
