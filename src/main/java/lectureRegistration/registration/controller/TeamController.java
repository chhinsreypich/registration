package lectureRegistration.registration.controller;

import lectureRegistration.registration.domain.Member;
import lectureRegistration.registration.domain.Team;
import lectureRegistration.registration.repository.MemberRepository;
import lectureRegistration.registration.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @GetMapping("/new")
    public String createForm(@RequestParam Long memberId, Model model) {
        model.addAttribute("team", new Team());
        model.addAttribute("memberId", memberId); // add this
        return "teams/createTeamForm";
    }

    // handle form submit
    @PostMapping("/new")
    public String create(@RequestParam String name,
                         @RequestParam String username,
                         @RequestParam String password,
                         @RequestParam Long memberId, // add this
                         Model model) {

        // Find member by username
        Member creator = memberRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        // Verify password
        if (!creator.getPassword().equals(password)) {
            model.addAttribute("error", "Wrong Password");
            return "teams/createTeamForm";
        }
        Team team = new Team();
        team.setName(name);
        team.setCreator(creator);
        teamRepository.save(team);
        return "redirect:/teams/list?memberId=" + memberId;
    }

    // show team list
    @GetMapping("/list")
    public String list(@RequestParam Long memberId, Model model) {
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("memberId", memberId);
        return "teams/teamList";
    }

    @GetMapping("/team-home")
    public String teamHome(@RequestParam Long memberId, Model model) {
        model.addAttribute("memberId", memberId);
        model.addAttribute("teams", teamRepository.findAll());
        return "teams/team-home";
    }

    @GetMapping("/{id}")
    public String teamDetail(@PathVariable Long id,
                             @RequestParam Long memberId,
                             Model model) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        model.addAttribute("team", team);
        model.addAttribute("members", team.getMemberList());
        model.addAttribute("memberId", memberId);

        return "teams/teamDetail";
    }
}
