package com.example.toyspring;

import com.example.toyspring.nplus.Member;
import com.example.toyspring.nplus.MemberRepository;
import com.example.toyspring.nplus.Team;
import com.example.toyspring.nplus.TeamRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class CallTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    @PersistenceContext
    private EntityManager entityManager;

//    public static Team inserted;

    public void setUp() {
        teamRepository.deleteAll();

        Team inserted = new Team();
        inserted.setId("ID_Aespa");
        inserted.setName("Aespa");

        Member karina = new Member("Karina");
        Member winter = new Member("Winter");
        Member ningning = new Member("Ningning");
        Member giselle = new Member("Giselle");

        inserted.addMember(karina);
        inserted.addMember(winter);
        inserted.addMember(ningning);
        inserted.addMember(giselle);

        teamRepository.save(inserted);


        Team newJeans = new Team();
        newJeans.setId("ID_NewJeans");
        newJeans.setName("New Jeans");

        Member minzi = new Member("민지");
        Member haerin = new Member("해린");
        Member hani = new Member("하니");
        Member daniel = new Member("Daniel");
        Member haein = new Member("혜인");

        newJeans.addMember(minzi);
        newJeans.addMember(haerin);
        newJeans.addMember(hani);
        newJeans.addMember(daniel);
        newJeans.addMember(haein);

        teamRepository.save(newJeans);

        Team stayc = new Team();
        stayc.setId("ID_STAYC");
        stayc.setName("STAYC");

        Member sumin = new Member("수민");
        Member sieun = new Member("시은");
        Member isa = new Member("ISA");
        Member seeun = new Member("세은");
        Member yoon = new Member("윤");
        Member jaehee = new Member("재희");

        stayc.addMember(sumin);
        stayc.addMember(sieun);
        stayc.addMember(isa);
        stayc.addMember(seeun);
        stayc.addMember(yoon);
        stayc.addMember(jaehee);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("대표적인 N+1")
    @Transactional
    public void n_plus_one() {

        List<Team> allTeams = teamRepository.findAll();

    }


    @Test
    @DisplayName("fetch join으로 N+1 해결하기")
    @Transactional
    public void n_plus_one_jpql_fetch_join() {

        entityManager.createQuery("select t from Team t join fetch t.members", Team.class)
                .getResultList()
                .stream().flatMap(team -> team.getMembers().stream())
                .forEach(member -> {
                    assertThat(Hibernate.isInitialized(member), is(true));
                });
    }

    @Test
    @DisplayName("FetchType.LAZY여도 연관관계 주인이 아니면 N+1 발생")
    @Transactional
    public void n_plus_one_when_lazy() {

        // N+1 발생
        Team aespa = teamRepository.findById("ID_Aespa")
                .orElseThrow(() -> new RuntimeException("Team not found"));

        // 연관 객체를 프록시가 아닌 실제 객체로 가짐
        aespa.getMembers().stream().forEach(member -> {
            assertThat(Hibernate.isInitialized(member), is(true));
        });

    }

    @Test
    @DisplayName("FK가 있는 쪽에 질의")
    @Transactional
    public void n_plus_one_when_lazy_not() {

        // N+1 발생 안함
        Member karina = memberRepository.findById("ID_Karina")
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // 연관 객체를 프록시로 가짐
        assertThat(Hibernate.isInitialized(karina.getTeam()), is(false));

        // 프록시 : 연관 객체의 ID만 가지고 있음
        karina.getTeam().getId();

        // 연관 객체를 프록시로 가짐
        assertThat(Hibernate.isInitialized(karina.getTeam()), is(false));
    }

    @Test
    @DisplayName("FetchType.LAZY 는 연관객체를 proxy로 가지고 있다.")
    @Transactional
    public void lazy_loading() {

        // N+1 발생 안함
        Member karina = memberRepository.findById("ID_Karina")
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // 연관 객체를 프록시로 가짐
        assertThat(Hibernate.isInitialized(karina.getTeam()), is(false));

        // 프록시 : 연관 객체의 ID만 가지고 있음
        karina.getTeam().getId();

        // 연관 객체를 프록시로 가짐
        assertThat(Hibernate.isInitialized(karina.getTeam()), is(false));

        // lazy loading 발생
        karina.getTeam().getName();

        // 연관 객체는 더이상 프록시가 아님
        assertThat(Hibernate.isInitialized(karina.getTeam()), is(true));
    }


    @Test
    @DisplayName("lazy여도 N+1 이 발생하는 예제")
    @Transactional
    public void n_plus_one_simple() {

        // N+1 발생
        Team aespa = teamRepository.findById("ID_Aespa")
                .orElseThrow(() -> new RuntimeException("Team not found"));

        // 연관 객체는 프록시가 아닌 실제 객체
        aespa.getMembers().stream().forEach(member -> {
            assertThat(Hibernate.isInitialized(member), is(false));
        });

    }

    @Test
    @DisplayName("FetchType.EAGER로 설정해도 N+1로 조회하지롱~")
    @Transactional
    public void n_plus_one_jpql_when_eager() {

        entityManager.createQuery("select t from Team t where t.id = :id", Team.class)
                .setParameter("id", "ID_Aespa")
                .getSingleResult()
                .getMembers()
                .stream()
                .forEach(member -> {
                    // N+1 : 연관객체가 프록시가 아닌 실제 객체
                    assertThat(Hibernate.isInitialized(member), is(true));
                });
    }

    @Test
    @DisplayName("FetchType.Lazy로 설정해도, 연관관계주인이 아니면 N+1")
    @Transactional
    public void n_plus_one_jpql_when_lazy() {

        entityManager.createQuery("select t from Team t where t.id = :id", Team.class)
                .setParameter("id", "ID_Aespa")
                .getSingleResult()
                .getMembers()
                .stream()
                .forEach(member -> {
                    // N+1 : 연관객체가 프록시가 아닌 실제 객체
                    assertThat(Hibernate.isInitialized(member), is(true));
                });
    }

    @Test
    @DisplayName("FetchType.Lazy로 설정해도, 연관관계주인이 아니면 fetchjoin으로 해결")
    @Transactional
    public void n_plus_one_jpql_when_lazy_sol() {

        entityManager.createQuery("select t from Team t join fetch t.members where t.id = :id", Team.class)
                .setParameter("id", "ID_Aespa")
                .getSingleResult()
                .getMembers()
                .stream()
                .forEach(member -> {
                    // N+1 X
                    assertThat(Hibernate.isInitialized(member), is(false));
                });
    }

    @Test
    @DisplayName("@BatchSize, FetchMode.SUBSELECT 활용")
    @Transactional
    public void use_batch_size() {

        List<Team> allTeams = teamRepository.findAll();

        allTeams.stream()
                .flatMap(team -> team.getMembers().stream())
                .forEach(member -> {
                    assertThat(Hibernate.isInitialized(member), is(true));
                });
    }


}
