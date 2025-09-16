package lectureRegistration.registration.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public record TeamDTO(
        String name,
        LocalDateTime createdAt,
        Long creatorId,
        List<MemberDTO> members
) {
}
