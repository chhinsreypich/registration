package lectureRegistration.registration.service;

import jakarta.validation.Valid;
import lectureRegistration.registration.api.dto.LectureDTO;
import lectureRegistration.registration.api.dto.MemberDTO;
import lectureRegistration.registration.domain.Lecture;
import lectureRegistration.registration.domain.Member;
import lectureRegistration.registration.domain.Team;
import lectureRegistration.registration.repository.MemberRepository;
import lectureRegistration.registration.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    public Long register(MemberDTO dto){
        Team team = teamRepository.findById(dto.teamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));

        Member member = new Member();
        member.setName(dto.name());
        member.setAge(dto.age());
        member.setAddress(dto.address());
        member.setPassword(dto.password());
        member.setCreatedAt(dto.createdAt() != null ? dto.createdAt() : LocalDateTime.now());
        member.setTeam(team);

        return memberRepository.save(member).getId();
    }

    // list all members
    public List<MemberDTO> listAll() {
        return memberRepository.findAll().stream()
                .map(m -> new MemberDTO(
                        m.getName(),
                        m.getAge(),
                        m.getAddress(),
                        m.getPassword(),
                        m.getCreatedAt(),
                        m.getTeam() != null ? m.getTeam().getId() : null
                ))
                .toList();
    }

    // update member
    public void update(Long id, @Valid MemberDTO dto){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        member.setName(dto.name());
        member.setAge(dto.age());
        member.setAddress(dto.address());
        if (dto.password() != null){
            member.setPassword(dto.password());;
        }

        if (dto.teamId() != null){
            Team team = teamRepository.findById(dto.teamId())
                    .orElseThrow(() -> new RuntimeException("Team not Found"));
            member.setTeam(team);
        }
        memberRepository.save(member);
    }

    // get enrolled lectures for a member
    public List<LectureDTO> getEnrolledLectures(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        return member.getRegistration().stream()
                .map(r -> new LectureDTO(
                        r.getLecture().getTitle(),
                        r.getLecture().getContent(),
                        r.getLecture().getCreatedAt()
                ))
                .toList();
    }

    // find member entity by ID (for internal use)
    public Member findEntityById (Long memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not Found"));
    }

    // login
    public Member login (String name, String password){
        Member member = memberRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        if (!member.getPassword().equals(password)) {
            throw new RuntimeException("Wrong Password");
        }
        return member;
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }
}

