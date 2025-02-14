package com.example.repository;

import com.example.entity.Message;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>  {
    boolean existsById(int id); //  Check if message exists
    @Query("SELECT m FROM Message m WHERE m.senderId = :userId")
    List<Message> findMessagesByUserId(@Param("userId") int userId);


    @Transactional
    @Modifying
    @Query("DELETE FROM Message m WHERE m.id = :id")
    int deleteMessageById(@Param("id") int id);

}
