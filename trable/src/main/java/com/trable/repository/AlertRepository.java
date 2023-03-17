package com.trable.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trable.entity.Alert;

public interface AlertRepository extends JpaRepository<Alert, Long>{

}
