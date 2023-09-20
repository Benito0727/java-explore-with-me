package ru.practicum.ewm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
}
