package com.LMS.repository;

import com.LMS.entity.HiredLabour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HiredLabourRepository extends JpaRepository<HiredLabour, Long> {
}
