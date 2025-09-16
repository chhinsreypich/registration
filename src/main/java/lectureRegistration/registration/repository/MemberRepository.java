package lectureRegistration.registration.repository;

import lectureRegistration.registration.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
     Optional<Member> findByName(String name);
     @Query("SELECT m FROM Member m LEFT JOIN FETCH m.registration WHERE m.id = :id")
     Optional<Member> findByIdWithRegistrations(@Param("id") Long id);

}
