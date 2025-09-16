package lectureRegistration.registration.api.controller;

import jakarta.validation.Valid;
import lectureRegistration.registration.api.dto.*;
import lectureRegistration.registration.domain.*;
import lectureRegistration.registration.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/registrations")
public class RegistrationApiController {
    private final RegistrationService registrationService;

    // enroll a member into a lecture
    @PostMapping("/add")
    public CreateRegistrationResponse enroll(@RequestBody @Valid RegistratoionDTO dto) {
        Long id = registrationService.enroll(
                dto.memberId(),
                dto.lectureId()
        );
        return new CreateRegistrationResponse(id, "Member enrolled successfully");
    }

    // list all registrations
    @GetMapping("/list")
    public List<Registration> listAll(){
        return registrationService.listAll();
    }

    // get students in a specific member
    @GetMapping("/member/{memberId}")
    public List<LectureDTO> getLecturesByMember(@PathVariable  Long memberId){
        return registrationService.getEnrolledLectures(memberId);
    }
    // get students in a specific lecture
    @GetMapping("/lecture/{lectureId}")
    public List<MemberDTO> getStudentByLecture(@PathVariable Long lectureId){
        return registrationService.getStudents(lectureId);
    }
    public record CreateRegistrationResponse(Long id, String messsage){}
}
