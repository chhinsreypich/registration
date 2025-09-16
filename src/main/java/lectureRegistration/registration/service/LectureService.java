package lectureRegistration.registration.service;

import lectureRegistration.registration.api.dto.LectureDTO;
import lectureRegistration.registration.api.dto.MemberDTO;
import lectureRegistration.registration.domain.Lecture;
import lectureRegistration.registration.domain.Member;
import lectureRegistration.registration.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;

    // register lecture
    public Long register (LectureDTO dto){
        Lecture lecture = new Lecture();
        lecture.setTitle(dto.title());
        lecture.setContent(dto.content());
        lecture.setCreatedAt(LocalDateTime.now());
        return lectureRepository.save(lecture).getId();
    }
    // Update lecture
    public void update (Long id, LectureDTO dto ){
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lecture not found"));
        lecture.setTitle(dto.title());
        lecture.setContent(dto.content());
        lecture.setCreatedAt(LocalDateTime.now());
        lectureRepository.save(lecture);
    }

    public List<LectureDTO>  listAll(){
        return lectureRepository.findAll().stream()
                .map(l -> new LectureDTO(
                        l.getTitle(),
                        l.getContent(),
                        l.getCreatedAt()
                ))
                .toList();
    }

    public List<MemberDTO> getStudent(Long lectureId){
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new RuntimeException("Lecture not found"));
        return lecture.getRegistrations().stream()
                .map(m -> new MemberDTO(
                        m.getMember().getName(),
                        m.getMember().getAge(),
                        m.getMember().getAddress(),
                        m.getMember().getPassword(),
                        m.getMember().getCreatedAt(),
                        m.getMember().getTeam() != null ? m.getMember().getTeam().getId() : null
                ))
                .toList();
    }
}

