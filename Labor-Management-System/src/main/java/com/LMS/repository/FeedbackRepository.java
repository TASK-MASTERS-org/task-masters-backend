package com.LMS.repository;

import com.LMS.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query("SELECT f FROM Feedback f JOIN f.hiredLabour hl WHERE hl.user.id = :userId")
    List<Feedback> findAllFeedbacksByUserId(@Param("userId") Long userId);
}
