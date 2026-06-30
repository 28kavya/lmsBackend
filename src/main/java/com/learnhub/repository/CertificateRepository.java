package com.learnhub.repository;

import com.learnhub.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository
        extends JpaRepository<Certificate, Long> {

    List<Certificate> findByStudentId(Long studentId);
}
