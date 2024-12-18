package com.project.midtrans2.usermanagement.repository;


import com.project.midtrans2.usermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    // Tidak perlu modifikasi karena JPA mendukung primary key tipe String
}
