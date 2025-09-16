package lectureRegistration.registration.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Team {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Member creator;

    @OneToMany(mappedBy = "team")
    private List<Member> memberList = new ArrayList<>();
}
