package ru.practicum.ewm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
