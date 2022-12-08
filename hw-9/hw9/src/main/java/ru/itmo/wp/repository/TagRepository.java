package ru.itmo.wp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itmo.wp.domain.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query(value = "SELECT name FROM tag", nativeQuery = true)
    List<String> findAllNames();

    Tag findByName(String name);
}
