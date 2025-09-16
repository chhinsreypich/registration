package lectureRegistration.registration.repository;

import lectureRegistration.registration.domain.Member;
import lectureRegistration.registration.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByCreator(Member creator);
}
