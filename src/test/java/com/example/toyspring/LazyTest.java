package com.example.toyspring;

import com.example.toyspring.domain.Child1;
import com.example.toyspring.domain.Child2;
import com.example.toyspring.domain.GrandParent;
import com.example.toyspring.domain.Parent;
import com.example.toyspring.repository.ParentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import org.hibernate.proxy.HibernateProxy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@Rollback(value = false)
public class LazyTest {

    @Autowired
    private ParentRepository parentRepository;


    @PersistenceUnit
    private EntityManagerFactory emf;

    @PersistenceContext
    private EntityManager em;


    @Test
    @Transactional
    public void lazyTest() {

//        Parent parent1 = new Parent(1L, "부모1");
//
//        Child1 child1_1_1 = new Child1(parent1, 1, "자식 1-1");
//        Child1 child1_1_2 = new Child1(parent1, 2, "자식 1-2");
//        Child1 child1_1_3 = new Child1(parent1, 3, "자식 1-3");
//
//        parent1.setChild1s(List.of(child1_1_1, child1_1_2, child1_1_3));
//
//        Child2 child2_1_1 = new Child2(1L, 1, "자식1-1", parent1);
//        Child2 child2_1_2 = new Child2(2L, 2, "자식1-2", parent1);
//        Child2 child2_1_3 = new Child2(3L, 3, "자식1-3", parent1);
//
//        parent1.setChild2s(List.of(child2_1_1, child2_1_2, child2_1_3));
//        GrandParent gp = new GrandParent(1L, "할아버지");
//        parent1.setGrandParent(gp);
//
//        parentRepository.save(parent1);
        Long parentID = 1L;

        Parent parent = parentRepository.findById(parentID).get();

        assertThat(parent.getGrandParent(), instanceOf(HibernateProxy.class));

        assertThat(parent.getChild2s(), instanceOf(HibernateProxy.class));

        Boolean isProxy1 = !emf.getPersistenceUnitUtil().isLoaded(parent.getChild2s());
        assertThat(isProxy1, is(true));

        Boolean isProxy2 = !emf.getPersistenceUnitUtil().isLoaded(parent.getChild2s());
        assertThat(isProxy2, is(true));

        parent.getChild1s().get(0);
        Boolean nowReal = emf.getPersistenceUnitUtil().isLoaded(parent.getChild1s());
        assertThat(nowReal, is(true));

    }


}
