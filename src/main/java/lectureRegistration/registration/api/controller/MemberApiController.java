package lectureRegistration.registration.api.controller;

import jakarta.validation.Valid;
import lectureRegistration.registration.api.dto.LectureDTO;
import lectureRegistration.registration.api.dto.MemberDTO;
import lectureRegistration.registration.domain.Member;
import lectureRegistration.registration.repository.MemberRepository;
import lectureRegistration.registration.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberApiController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    // register (sign up)
    @PostMapping("/add")
    public CreateMemberResponse register(@RequestBody @Valid MemberDTO dto){
        Long id = memberService.register(dto);
        return new CreateMemberResponse(id, "Successfully");
    }

    // modify member
    @PutMapping("/{memberId}/edit")
    public UpdateMemberResponse update (@PathVariable Long memberId, @RequestBody @Valid MemberDTO dto){
        memberService.update(memberId, dto);
        return new UpdateMemberResponse(memberId, "Member Updated Successfully");
    }

    // show all member
    @GetMapping("/list")
    public List<MemberDTO> getAllMembers() {
        return memberService.listAll();
    }

    // login
    @PostMapping("/login")
    public LoginResponse login ( @RequestBody MemberDTO dto){
        Member member = memberService.login(dto.name(), dto.password());
        return new LoginResponse(member.getId(), member.getName(), "Login Sucessfully");
    }
    public record CreateMemberResponse (Long id, String message){}
    public record UpdateMemberResponse (Long memberId, String message){}
    public record LoginResponse (Long memberId, String name, String message){}
}
