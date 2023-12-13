package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("FROM Message WHERE message_id = :idVar")
    Message findByMessage_id(@Param("idVar")int idNum);
    @Query("FROM Message WHERE posted_by = :idVar")
    List<Message> findByPostedBy(@Param("idVar")int idNum);
}
