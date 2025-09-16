package lectureRegistration.registration.repository;

import lectureRegistration.registration.domain.Lecture;
import lectureRegistration.registration.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Optional<Member> findByTitle(String title);
}
