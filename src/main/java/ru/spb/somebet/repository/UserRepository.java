package ru.spb.somebet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.spb.somebet.model.User;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {
}
