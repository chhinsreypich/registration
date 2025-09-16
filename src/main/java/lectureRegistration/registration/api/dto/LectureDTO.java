package lectureRegistration.registration.api.dto;

import java.time.LocalDateTime;

public record LectureDTO(
//        Long id, if we want to response(fetch/display)
        String title,
        String content,
        LocalDateTime createdAt
) {

}
