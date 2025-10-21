package com.prouvetech.prouvetech.controller;

import com.prouvetech.prouvetech.dto.ProjectDTO;
import com.prouvetech.prouvetech.dto.UserDTO;
import com.prouvetech.prouvetech.model.Project;
import com.prouvetech.prouvetech.model.User;
import com.prouvetech.prouvetech.service.ProjectService;
import com.prouvetech.prouvetech.service.UserService;
import com.prouvetech.prouvetech.utils.ResponseMessage;
import com.prouvetech.prouvetech.utils.Uploads;

import java.io.IOException;
import java.util.List;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    public UserController() {
    }

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private Uploads uploads;

    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> addUser(@RequestBody UserDTO userDTO) {

        User newUser = new User(userDTO.getFirstname(), userDTO.getLastname(),
                userDTO.getMail(), userDTO.getPassword());
        ResponseMessage result = userService.createUser(newUser);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMessage> updateUser(@ModelAttribute UserDTO userDTO) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getUserByMail(email);

        Tika tika = new Tika();

        String detectedTypePhoto = tika.detect(userDTO.getPhoto().getInputStream());

        if (!detectedTypePhoto.startsWith("image/")) {
            ResponseMessage message = new ResponseMessage(false, "Le fichier doit être une image");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        String newphoto = uploads.updateFile(userDTO.getPhoto(), user.getPhoto());

        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setTitle(userDTO.getTitle());
        user.setDescription(userDTO.getDescription());
        user.setPhoto(newphoto);
        ResponseMessage result = userService.updateUser(user);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }

    @GetMapping("/details/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {

        User user = userService.getUser(id);

        UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getMail(),
                user.getTitle(),
                user.getDescription(),
                user.getPhoto());

        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfil() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByMail(authentication.getName());

        UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getMail(),
                user.getTitle(),
                user.getDescription(),
                user.getPhoto());

        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/myprojects")
    public ResponseEntity<List<ProjectDTO>> getMyProjects() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByMail(authentication.getName());

        UserDTO userDTO = new UserDTO(user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getMail(),
                user.getTitle(),
                user.getDescription(),
                user.getPhoto());

        List<Project> projects = projectService.getMyProjects(user);
        List<ProjectDTO> projects_dto = projects.stream()
                .map(p -> new ProjectDTO(
                        p.getId(),
                        p.getName(),
                        p.getDescription(),
                        p.getVideo(),
                        p.getThumbnail(),
                        p.getSourceCode(),
                        userDTO))
                .toList();

        return ResponseEntity.ok(projects_dto);

    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<List<ProjectDTO>> getProjectUser(@PathVariable long id) {

        User user = userService.getUser(id);

        List<Project> projects = projectService.getMyProjects(user);

        UserDTO userDTO = new UserDTO(user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getMail(),
                user.getTitle(),
                user.getDescription(),
                user.getPhoto());
        List<ProjectDTO> projects_dto = projects.stream()
                .map(p -> new ProjectDTO(
                        p.getId(),
                        p.getName(),
                        p.getDescription(),
                        p.getVideo(),
                        p.getThumbnail(),
                        p.getSourceCode(),
                        userDTO))
                .toList();

        return ResponseEntity.ok(projects_dto);

    }

    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> dtos = users.stream()
                .map(u -> new UserDTO(
                        u.getId(),
                        u.getFirstname(),
                        u.getLastname(),
                        u.getMail(),
                        u.getTitle(),
                        u.getDescription(),
                        u.getPhoto()))
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(@RequestBody UserDTO userDTO) {

        User user = new User(userDTO.getMail(), userDTO.getPassword());
        ResponseMessage token = userService.authenticateUser(user.getMail(), user.getPassword());

        if (!token.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(token);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsers(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long statusId) {

        List<User> users = userService.searchUser(title);
        List<UserDTO> userDTOs = users.stream()
                .map(u -> new UserDTO(
                        u.getId(),
                        u.getFirstname(),
                        u.getLastname(),
                        u.getMail(),
                        u.getTitle(),
                        u.getDescription(),
                        u.getPhoto()))
                .toList();

        return ResponseEntity.ok(userDTOs);
    }

}
