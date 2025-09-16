package lectureRegistration.registration.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Lecture {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Registration> registrations = new ArrayList<>();
}
