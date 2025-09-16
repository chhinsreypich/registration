package lectureRegistration.registration.api.dto;

public record MemberSignupDTO(
        String name,
        int age,
        String address,
        String password,
        Long teamId
) {
}
