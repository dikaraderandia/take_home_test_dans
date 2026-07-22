package com.dikara.dans.repository;

import com.dikara.dans.entity.Event;
import com.dikara.dans.entity.EventRegistration;
import com.dikara.dans.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRegistrationRepository extends JpaRepository<EventRegistration,Long> {

    boolean existsByUserAndEvent(User user, Event event);
}
