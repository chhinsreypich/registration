package lectureRegistration.registration.api.controller;

import jakarta.validation.Valid;
import lectureRegistration.registration.api.dto.LectureDTO;
import lectureRegistration.registration.api.dto.MemberDTO;
import lectureRegistration.registration.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lectures")
public class LectureApiController {
    private final LectureService lectureService;

    @PostMapping("/add")
    public Long register (@RequestBody @Valid LectureDTO dto){
        return lectureService.register(dto);
    }

    // modify lecture
    @PutMapping("/{id}/edit")
    public UpdateLectureResponse edit (@PathVariable Long id, @RequestBody @Valid LectureDTO dto){
        lectureService.update(id, dto);
        return new UpdateLectureResponse(id, "Lecture Update Successfully");
    }

    // list lecture
    @GetMapping("/list")
    public List<LectureDTO> list(@PathVariable Long id) {
        return lectureService.listAll();
    }

    // list member in lecture
    @GetMapping("/{id}/students")
    public List<MemberDTO> students(@PathVariable Long id) {
        return lectureService.getStudent(id);
    }
    public record CreateLectureResponse(Long id, String message){}
    public record UpdateLectureResponse(Long id, String message){}
}
