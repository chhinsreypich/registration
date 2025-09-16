package lectureRegistration.registration.controller;

import lectureRegistration.registration.api.dto.MemberDTO;
import lectureRegistration.registration.api.dto.MemberSignupDTO;
import lectureRegistration.registration.domain.Lecture;
import lectureRegistration.registration.domain.Member;
import lectureRegistration.registration.domain.Registration;
import lectureRegistration.registration.domain.Team;
import lectureRegistration.registration.repository.MemberRepository;
import lectureRegistration.registration.repository.TeamRepository;
import lectureRegistration.registration.service.MemberService;
import lectureRegistration.registration.service.MemberSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberRepository memberRepository; // for login
    private final MemberSignUpService memberSignUpService; // for sign up and other acition
    private final TeamRepository teamRepository; // for team info

    @GetMapping("/signIn_Up")
    public String signInUpPage(Model model){
        model.addAttribute("teams", teamRepository.findAll()); // for signup form
        return "members/signIn_Up"; // your toggle page
    }

    // Process signup
    @PostMapping("/signIn_Up")
    public String signup(
            @RequestParam String name,
            @RequestParam int age,
            @RequestParam String address,
            @RequestParam String password,
            @RequestParam Long teamId,
            Model model
    ) {
        memberSignUpService.signup(new MemberSignupDTO(name, age, address, password, teamId));
        // after signup, you can return the same page and show login
        model.addAttribute("signupSuccess", true);
        return "members/signIn_Up";
    }

    // Process login
    @PostMapping("/login")
    public String login(@RequestParam String name,
                        @RequestParam String password,
                        Model model){
        Member member = memberRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        if (!member.getPassword().equals(password)){
            model.addAttribute("error", "Wrong password");
            return "members/signIn_up"; // stay on same page if login fails
        }
        model.addAttribute("member", member);
        return "redirect:/members/member-home?memberId=" + member.getId();
    }

    @GetMapping("/member-home")
    public String memberHome(@RequestParam Long memberId, Model model) {
        Member member = memberRepository.findByIdWithRegistrations(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        List<Team> myTeams = teamRepository.findByCreator(member);
        if (myTeams == null) {
            myTeams = new ArrayList<>();
        }

        List<Lecture> joinedLectures = member.getRegistration().stream()
                .map(Registration::getLecture)
                .toList();

        model.addAttribute("member", member);
        model.addAttribute("MemberId", member.getId());
        model.addAttribute("myTeams", myTeams);
        model.addAttribute("joinedLectures", joinedLectures); // add lectures to model

        return "members/member-home";
    }

    // edit
    @GetMapping("/edit")
    public String editForm(@RequestParam(required = false) Long memberId, Model model){
        if(memberId == null){
            return "redirect:/members/signIn_Up";
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not Found"));
        model.addAttribute("member", member);
        model.addAttribute("teams", teamRepository.findAll());
        return "members/edit-member";
    }

    // handle edit submission
    @PostMapping("/edit")
    public String edit(
            @RequestParam Long memberId,
            @RequestParam String name,
            @RequestParam int age,
            @RequestParam String address,
            @RequestParam Long teamId
    ){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        member.setName(name);
        member.setAge(age);;
        member.setAddress(address);
        member.setTeam(teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found")));
        memberRepository.save(member);
        return "redirect:/members/member-home?memberId=" + memberId;
    }

    // change password
    @GetMapping("/change-password")
    public String changePasswordForm(@RequestParam Long memberId, Model model){
        model.addAttribute("memberId", memberId);
        return "members/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword (
            @RequestParam Long memberId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword
    ) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("member not found"));
        if (!member.getPassword().equals(oldPassword)){
            throw new RuntimeException("Old Password is incorrect");
        }
        member.setPassword(newPassword);
        memberRepository.save(member);
        return "redirect:/members/member-home?memberId=" + memberId;
    }

    @GetMapping("/logout")
    public String logout () {
        return "redirect:/members/login";
    }
}












//package lectureRegistration.registration.controller;
//
//import lectureRegistration.registration.api.dto.MemberDTO;
//import lectureRegistration.registration.api.dto.MemberSignupDTO;
//import lectureRegistration.registration.domain.Member;
//import lectureRegistration.registration.repository.MemberRepository;
//import lectureRegistration.registration.repository.TeamRepository;
//import lectureRegistration.registration.service.MemberService;
//import lectureRegistration.registration.service.MemberSignUpService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.time.LocalDateTime;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/members")
//public class MemberController {
//    private final MemberRepository memberRepository; // for login
//    private final MemberSignUpService memberSignUpService; // for sign up and other acition
//    private final TeamRepository teamRepository; // for team info
//
//    @GetMapping("/signIn_up")
//    public String home () { return "members/signIn_up";}
//
//    @GetMapping("/signup")
//    public String signupPage(Model model){
//        model.addAttribute("team", teamRepository.findAll());
//        return "members/signup";
//    }
//    @PostMapping("/signup")
//    public String signup(
//            @RequestParam String name,
//            @RequestParam int age,
//            @RequestParam String address,
//            @RequestParam String password,
//            @RequestParam Long teamId,
//            Model model
//    ) {
//        memberSignUpService.signup(new MemberSignupDTO(name, age, address, password, teamId));
//        return "redirect:/members/login";
//    }
//    // show login page
//    @GetMapping("/login")
//    public String loginPage (){
//        return "members/login";
//    }
//    // process login
//    @PostMapping("/login")
//    public String login (@RequestParam String name, @RequestParam String password, Model model){
//        Member member = memberRepository.findByName(name)
//                .orElseThrow(() -> new RuntimeException("Member not found"));
//        if (!member.getPassword().equals(password)){
//            model.addAttribute("error", "Wrong password");
//            return "members/login";
//        }
//        model.addAttribute("member", member);
//        return "members/member-home";
//    }
//
//    @GetMapping("/member-home")
//    public String memberHome(@RequestParam Long memberId, Model model) {
//        Member member = memberRepository.findByIdWithRegistrations(memberId)
//                .orElseThrow(() -> new RuntimeException("Member not found"));
//        model.addAttribute("member", member);
//        return "members/member-home";
//    }
//
//    // show edit form
//    @GetMapping("/edit")
//    public String editForm(@RequestParam Long memberId, Model model){
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new RuntimeException("Member not Found"));
//        model.addAttribute("member", member);
//        model.addAttribute("teams", teamRepository.findAll());
//        return "members/edit-member";
//    }
//
//    // handle edit submission
//    @PostMapping("/edit")
//    public String edit(
//            @RequestParam Long memberId,
//            @RequestParam String name,
//            @RequestParam int age,
//            @RequestParam String address,
//            @RequestParam Long teamId
//    ){
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new RuntimeException("Member not found"));
//        member.setName(name);
//        member.setAge(age);;
//        member.setAddress(address);
//        member.setTeam(teamRepository.findById(teamId)
//                .orElseThrow(() -> new RuntimeException("Team not found")));
//        memberRepository.save(member);
//        return "redirect:/members/member-home?memberId=" + memberId;
//    }
//
//    // change password
//    @GetMapping("/change-password")
//    public String changePasswordForm(@RequestParam Long memberId, Model model){
//        model.addAttribute("memberId", memberId);
//        return "members/change-password";
//    }
//
//    @PostMapping("/change-password")
//    public String changePassword (
//            @RequestParam Long memberId,
//            @RequestParam String oldPassword,
//            @RequestParam String newPassword
//    ) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new RuntimeException("member not found"));
//        if (!member.getPassword().equals(oldPassword)){
//            throw new RuntimeException("Old Password is incorrect");
//        }
//        member.setPassword(newPassword);
//        memberRepository.save(member);
//        return "redirect:/members/member-home?memberId=" + memberId;
//    }
//
//    @GetMapping("/logout")
//    public String logout () {
//        return "redirect:/members/login";
//    }
//}
