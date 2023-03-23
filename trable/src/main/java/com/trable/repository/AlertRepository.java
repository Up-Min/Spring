package com.trable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trable.entity.Alert;

public interface AlertRepository extends JpaRepository<Alert, Long>{

		@Query(value = "select * from alert where alertcheck = 'N' and member_id = :memid", nativeQuery = true)
		List<Alert> findAlertlistbymemid(@Param("memid") Long memid);
}
