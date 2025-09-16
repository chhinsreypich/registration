package lectureRegistration.registration.api.controller;

import jakarta.validation.Valid;
import lectureRegistration.registration.api.dto.TeamDTO;
import lectureRegistration.registration.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teams")
public class TeamApiController {
    private final TeamService teamService;

    // Register team
    @PostMapping("/add")
    public CreateTeamResponse register(@RequestBody @Valid TeamDTO dto) {
        Long id = teamService.register(dto);
        return new CreateTeamResponse(id, "Team Created Successfully");
    }

    // Update team
    @PutMapping("/{id}/edit")
    public UpdateTeamResponse edit(@PathVariable Long id, @RequestBody @Valid TeamDTO dto){
        teamService.update(id, dto);
        return new UpdateTeamResponse(id, "Team Updated Successfully");
    }

    // List teams
    @GetMapping("/list")
    public List<TeamDTO> list() {
        return teamService.listAll();
    }

    // Response records
    public record CreateTeamResponse(Long id, String message) {}
    public record UpdateTeamResponse(Long id, String message) {}
}








//package lectureRegistration.registration.api.controller;
//
//import jakarta.validation.Valid;
//import lectureRegistration.registration.api.dto.TeamDTO;
//import lectureRegistration.registration.service.TeamService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/teams")
//public class TeamApiController {
//    private final TeamService teamService;
//
//    // register
////    @PostMapping("/add")
////    public CreateTeamResponse register (@RequestBody @Valid TeamDTO dto) {
////        Long id = teamService.register(dto);
////        return new CreateTeamResponse(id, "Team Created Successfully");
////    }
//
//    // register
//    @PostMapping("/add")
//    public Long register(@RequestBody @Valid TeamDTO dto) {
//        return teamService.register(dto);
//    }
//
//    // modify
//    @PutMapping("/{id}/edit")
//    public UpdateTeamResponse edit (@PathVariable Long id, @RequestBody @Valid TeamDTO dto){
//        teamService.update(id, dto);
//        return new UpdateTeamResponse(id, "Team Updated Successfully");
//    }
//
//    // list
//    @GetMapping("/list")
//    public List<TeamDTO> list (){
//        return teamService.listAll();
//    }
//
////    public record CreateTeamResponse (Long id, String message){}
//    public record UpdateTeamResponse (Long id, String message){}
//}
