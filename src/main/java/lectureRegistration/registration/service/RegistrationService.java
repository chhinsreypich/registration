package lectureRegistration.registration.service;

import lectureRegistration.registration.api.dto.LectureDTO;
import lectureRegistration.registration.api.dto.MemberDTO;
import lectureRegistration.registration.domain.Lecture;
import lectureRegistration.registration.domain.Member;
import lectureRegistration.registration.domain.Registration;
import lectureRegistration.registration.repository.LectureRepository;
import lectureRegistration.registration.repository.MemberRepository;
import lectureRegistration.registration.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final MemberRepository memberRepository;
    private final LectureRepository lectureRepository;

    // enroll a member into a lecture
    public Long enroll ( Long memberId, Long lectureId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member Not Found"));

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new RuntimeException("Lecture not Found"));

        Registration registration = new Registration();
        registration.setMember(member);;
        registration.setLecture(lecture);
        registration.setEnrolledAt(LocalDateTime.now());
        return registrationRepository.save(registration).getId();
    }

//    // cancel enrollment
//    public void cancel (Long registrationId){
//        Registration registration = registrationRepository.findById(registrationId)
//                .orElseThrow(() -> new RuntimeException("Registration  not Found"));
//
//        registrationRepository.delete(registration);
//    }

    // get all lectures a member in enrolled in
    public List<LectureDTO> getEnrolledLectures(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        return member.getRegistration().stream()
                .map( r -> new LectureDTO(
                        r.getLecture().getTitle(),
                        r.getLecture().getContent(),
                        r.getLecture().getCreatedAt()
                )).toList();
    }

    // get all members enrolled in a lecture
    public List<MemberDTO> getStudents(Long lectureId){
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new RuntimeException("Lecture not found"));

        return lecture.getRegistrations().stream()
                .map(r -> new MemberDTO(
                        r.getMember().getName(),
                        r.getMember().getAge(),
                        r.getMember().getAddress(),
                        r.getMember().getPassword(),
                        r.getMember().getCreatedAt(),
                        r.getMember().getTeam() != null ? r.getMember().getTeam().getId() : null

                )).toList();
    }

    // list all registrations
    public List<Registration> listAll(){
        return registrationRepository.findAll();
    }
}
