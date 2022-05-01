package ru.spb.somebet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spb.somebet.model.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {
}
