package ru.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.entity.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Page<User> findAllByIdIn(Integer[] ids, Pageable pageable);
}
