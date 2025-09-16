package lectureRegistration.registration.api.dto;

import java.time.LocalDateTime;

public record RegistratoionDTO(
        Long memberId,
        Long lectureId,
        LocalDateTime enrolledAt
) {
}
