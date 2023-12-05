package com.example.toyspring.repository;

import com.example.toyspring.domain.Child2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Child2Repository extends JpaRepository<Child2, Long> {


}
