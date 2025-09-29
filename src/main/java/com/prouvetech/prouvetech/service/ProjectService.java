package com.prouvetech.prouvetech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prouvetech.prouvetech.model.Project;
import com.prouvetech.prouvetech.model.User;
import com.prouvetech.prouvetech.repository.ProjectRepository;
import com.prouvetech.prouvetech.repository.UserRepository;
import com.prouvetech.prouvetech.utils.ResponseMessage;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public Project getProject(long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucun projet trouvé avec l'ID : " + id));
    }

    public Project getMyProject(Long projectId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return projectRepository.findByIdAndUser(projectId, user)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé pour cet utilisateur"));
    }

    public List<Project> getAllProject() {
        return projectRepository.findAll();
    }

    public List<Project> getMyProjects(User user) {
        return projectRepository.findByUser(user);
    }

    public ResponseMessage addProject(Project project) {

        projectRepository.save(project);
        return new ResponseMessage(true,
                "Le projet '" + project.getName() + "' a été ajouté avec l'ID " + project.getId());
    }

    public ResponseMessage updateProject(Project project) {

        projectRepository.save(project);
        return new ResponseMessage(true, "Le projet '" + project.getName() + "'a été modifié");

    }

    public ResponseMessage deleteProject(Long id) {
        Project projet = getProject(id);
        if (!projectRepository.existsById(id)) {
            return new ResponseMessage(false, "Aucun projet trouvé avec l'ID : " + projet.getId());
        }
        projectRepository.deleteById(id);
        return new ResponseMessage(true,
                "Le project '" + projet.getName() + "'' avec l'ID " + projet.getId() + ", a été supprimé avec succès.");
    }

    public List<Project> searchProjects(String name, List<Long> toolIds) {
        if (name != null && toolIds != null && !toolIds.isEmpty()) {
            return projectRepository.findDistinctByNameContainingIgnoreCaseAndToolsIdIn(name, toolIds);
        } else if (name != null) {
            return projectRepository.findByNameContainingIgnoreCase(name);
        } else if (toolIds != null && !toolIds.isEmpty()) {
            return projectRepository.findDistinctByToolsIdIn(toolIds);
        } else {
            return projectRepository.findAll();
        }
    }
}
