package com.example.toyspring.repository;

import com.example.toyspring.domain.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {


}
