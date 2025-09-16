package lectureRegistration.registration.controller;
import lectureRegistration.registration.domain.Lecture;
import lectureRegistration.registration.domain.Member;
import lectureRegistration.registration.domain.Registration;
import lectureRegistration.registration.repository.LectureRepository;
import lectureRegistration.registration.repository.MemberRepository;
import lectureRegistration.registration.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registrations")
public class RegistrationController {

    private final RegistrationRepository registrationRepository;
    private final MemberRepository memberRepository;
    private final LectureRepository lectureRepository;

    // Show form to enroll a member in a lecture
    @GetMapping("/new")
    public String enrollForm(@RequestParam Long memberId, Model model) {
        List<Lecture> lectures = lectureRepository.findAll();
        model.addAttribute("lectures", lectures);
        model.addAttribute("memberId", memberId);
        return "registrations/enrollForm";
    }

    // Handle enrollment submission
    @PostMapping("/new")
    public String enroll(@RequestParam Long memberId, @RequestParam Long lectureId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new RuntimeException("Lecture not found"));

        Registration registration = new Registration();
        registration.setMember(member);
        registration.setLecture(lecture);
        registration.setEnrolledAt(LocalDateTime.now());
        registrationRepository.save(registration);

        return "redirect:/registrations/" + registration.getId() + "?memberId=" + memberId;
    }

    @GetMapping("/{id}")
    public String registrationDetail(@PathVariable Long id,
                                     @RequestParam Long memberId,
                                     Model model) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        List<Registration> registrations = registrationRepository.findByLectureId(registration.getLecture().getId());

        model.addAttribute("registration", registration);
        model.addAttribute("lecture", registration.getLecture());
        model.addAttribute("member", registration.getMember());
        model.addAttribute("memberId", memberId);
        model.addAttribute("registrations", registrations); // ✅ Add this

        return "registrations/registrationDetail";
    }

//    // Cancel a registration
//    @PostMapping("/{id}/cancel")
//    public String cancelRegistration(@PathVariable Long id,
//                                     @RequestParam Long memberId) {
//        Registration registration = registrationRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Registration not found"));
//        Long lectureId = registration.getLecture().getId();
//
//        registrationRepository.delete(registration);
//        return "redirect:/lectures/" + lectureId + "?memberId=" + memberId;
//    }
}


