package lectureRegistration.registration.api.dto;

import java.time.LocalDateTime;

public record MemberDTO (
//        Long id,
        String name,
        int age,
        String address,
        String password,
        LocalDateTime createdAt,
        Long teamId
){
}
