package com.example.toyspring.repository;

import com.example.toyspring.domain.Child1;
import com.example.toyspring.domain.id.Child1Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Child1Repository extends JpaRepository<Child1, Child1Id> {


}
