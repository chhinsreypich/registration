package lectureRegistration.registration.service;

import lectureRegistration.registration.api.dto.MemberDTO;
import lectureRegistration.registration.api.dto.MemberSignupDTO;
import lectureRegistration.registration.domain.Member;
import lectureRegistration.registration.repository.MemberRepository;
import lectureRegistration.registration.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberSignUpService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    public Member signup(MemberSignupDTO dto){
        Member member = new Member();
        member.setName(dto.name());
        member.setAge(dto.age());
        member.setAddress(dto.address());
        member.setPassword(dto.password());
        member.setCreatedAt(LocalDateTime.now());

        teamRepository.findById(dto.teamId())
                .ifPresent((member::setTeam));
        return memberRepository.save(member);
    }
}
