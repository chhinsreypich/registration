package lectureRegistration.registration.service;

import lectureRegistration.registration.api.dto.MemberDTO;
import lectureRegistration.registration.api.dto.TeamDTO;
import lectureRegistration.registration.domain.Member;
import lectureRegistration.registration.domain.Team;
import lectureRegistration.registration.repository.MemberRepository;
import lectureRegistration.registration.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    // register team
    public Long register (TeamDTO dto){
        Team team = new Team();
        team.setName(dto.name());
        team.setCreatedAt(LocalDateTime.now());

        if (dto.creatorId() != null) {
            Member creator = memberRepository.findById(dto.creatorId())
                    .orElseThrow(() -> new RuntimeException("Creator not found"));
            team.setCreator(creator);
        }

        return teamRepository.save(team).getId();
    }

    // update team
    public void update(Long id, TeamDTO dto){
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("team not found"));
        team.setName(dto.name());
        teamRepository.save(team);
    }

    // List all teams
    public List<TeamDTO> listAll() {
        return teamRepository.findAll().stream()
                .map(t -> new TeamDTO(
                        t.getName(),
                        t.getCreatedAt(),
                        t.getCreator() != null ? t.getCreator().getId() : null,  // ✅ include creator id
                        t.getMemberList().stream()
                                .map(m -> new MemberDTO(
                                        m.getName(),
                                        m.getAge(),
                                        m.getAddress(),
                                        m.getPassword(),
                                        m.getCreatedAt(),
                                        m.getTeam().getId()
                                ))
                                .toList()
                ))
                .toList();
    }
}
