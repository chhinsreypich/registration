package lectureRegistration.registration.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Locked;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int age;
    private String address;

    @Column(nullable = false)
    private String password;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Registration> registration = new ArrayList<>();
}
