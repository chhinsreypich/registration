package lectureRegistration.registration.component;

import jakarta.annotation.PostConstruct;
import lectureRegistration.registration.domain.Member;
import lectureRegistration.registration.repository.MemberRepository;
import org.springframework.stereotype.Component;
import lectureRegistration.registration.domain.Team;
import lectureRegistration.registration.domain.Lecture;
import lectureRegistration.registration.repository.TeamRepository;
import lectureRegistration.registration.repository.LectureRepository;

@Component
public class InitialData {
///  add some data to database
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final LectureRepository lectureRepository;

    public InitialData(TeamRepository teamRepository, MemberRepository memberRepository, LectureRepository lectureRepository) {
        this.teamRepository = teamRepository;
        this.memberRepository = memberRepository;
        this.lectureRepository = lectureRepository;
    }

    @PostConstruct
    public void init() {
        Team systemTeam = new Team();
        systemTeam.setName("System Team");
        teamRepository.save(systemTeam);

        Member systemMember = new Member();
        systemMember.setName("System");
        systemMember.setPassword("System");
        systemMember.setTeam(systemTeam);
        memberRepository.save(systemMember);


        Team team1 = new Team();
        team1.setName("Team A");
        team1.setCreator(systemMember);
        teamRepository.save(team1);

        Team team2 = new Team();
        team2.setName("Team B");
        team2.setCreator(systemMember);
        teamRepository.save(team2);

        Member member1 = new Member();
        member1.setName("Chyy");
        member1.setAge(22);
        member1.setAddress("Phnom Penh");
        member1.setPassword("2222");
        member1.setTeam(team1);
        memberRepository.save(member1);

        Lecture lecture1 = new Lecture();
        lecture1.setTitle("Math 101");
        lecture1.setContent("Introduction to Math");
        lectureRepository.save(lecture1);

        Lecture lecture2 = new Lecture();
        lecture2.setTitle("Physics 101");
        lecture2.setContent("Introduction to Physics");
        lectureRepository.save(lecture2);
    }
}
