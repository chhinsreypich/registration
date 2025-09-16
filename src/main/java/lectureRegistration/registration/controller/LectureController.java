package lectureRegistration.registration.controller;

import lectureRegistration.registration.domain.Lecture;
import lectureRegistration.registration.domain.Member;
import lectureRegistration.registration.domain.Registration;
import lectureRegistration.registration.repository.LectureRepository;
import lectureRegistration.registration.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lectures")
public class LectureController {

    private final LectureRepository lectureRepository;
    private final MemberRepository memberRepository;

    // Show form to create lecture
    @GetMapping("/new")
    public String createLectureForm(@RequestParam Long memberId, Model model){
        model.addAttribute("lecture", new Lecture());
        model.addAttribute("memberId", memberId); // pass memberId
        return "lectures/createLectureForm";
    }

    // Handle create lecture
    @PostMapping("/new")
    public String create(@ModelAttribute Lecture lecture, @RequestParam Long memberId){
        lectureRepository.save(lecture);
        return "redirect:/lectures/list?memberId=" + memberId;
    }

    // List all lectures
    @GetMapping("/list")
    public String list(@RequestParam Long memberId, Model model) {
        List<Lecture> lectures = lectureRepository.findAll();
        model.addAttribute("lectures", lectures);
        model.addAttribute("memberId", memberId);
        return "lectures/lectureList";
    }

    // Enroll member in a lecture
    @GetMapping("/enroll")
    public String enrollForm(@RequestParam Long memberId, Model model){
        List<Lecture> lectures = lectureRepository.findAll();
        model.addAttribute("lectures", lectures);
        model.addAttribute("memberId", memberId);
        return "lectures/enrollForm";
    }

    @PostMapping("/enroll")
    public String enroll(@RequestParam Long memberId, @RequestParam Long lectureId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not Found"));
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new RuntimeException("Lecture not Found"));

        Registration registration = new Registration();
        registration.setMember(member);
        registration.setLecture(lecture);
        registration.setEnrolledAt(LocalDateTime.now());

        member.getRegistration().add(registration);
        memberRepository.save(member);

        return "redirect:/lectures/" + lectureId + "?memberId=" + memberId;
    }

    // Show edit lecture form
    @GetMapping("/edit")
    public String editForm(@RequestParam Long lectureId, @RequestParam Long memberId, Model model) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new RuntimeException("Lecture not found"));
        model.addAttribute("lecture", lecture);
        model.addAttribute("memberId", memberId);
        return "lectures/editLectureForm";
    }

    // Handle edit submission
    @PostMapping("/edit")
    public String edit(@RequestParam Long lectureId,
                       @RequestParam String title,
                       @RequestParam String content,
                       @RequestParam Long memberId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new RuntimeException("Lecture not found"));
        lecture.setTitle(title);
        lecture.setContent(content);
        lectureRepository.save(lecture);

        return "redirect:/lectures/list?memberId=" + memberId;
    }

    // Show lecture detail page
    @GetMapping("/{id}")
    public String lectureDetail(@PathVariable Long id,
                                @RequestParam Long memberId,
                                Model model) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lecture not found"));

        model.addAttribute("lecture", lecture);
        model.addAttribute("registrations", lecture.getRegistrations());
        model.addAttribute("memberId", memberId);

        return "registrations/registrationDetail";
    }

    @PostMapping("/delete/{id}")
    public String deleteLecture(@PathVariable Long id, @RequestParam Long memberId) {
        lectureRepository.deleteById(id);
        return "redirect:/lectures/list?memberId=" + memberId;
    }
}
