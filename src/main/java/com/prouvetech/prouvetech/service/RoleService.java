package com.prouvetech.prouvetech.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prouvetech.prouvetech.model.Role;
import com.prouvetech.prouvetech.model.Status;
import com.prouvetech.prouvetech.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Role findByName(String role_name) {
        return roleRepository.findByName(role_name)
                .orElseThrow(() -> new RuntimeException(role_name + " est introuvable"));
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    public Role updateRole(Long id, Role updatedRole) {
        return roleRepository.findById(id)
                .map(role -> {
                    role.setName(updatedRole.getName());
                    return roleRepository.save(role);
                })
                .orElseThrow(() -> new RuntimeException("Rôle non trouvé avec l'ID : " + id));
    }

    public List<Role> getByRolesIds(List<Long> ids) {
        return this.roleRepository.findAllById(ids);
    }

    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Rôle non trouvé avec l'ID : " + id);
        }
        roleRepository.deleteById(id);
    }

    public Optional<Role> getRoleByStatus(Status status) {
        String statusName = status.getName().toLowerCase();

        if (statusName.equalsIgnoreCase("developer")) {
            return roleRepository.findByName("ROLE_DEVELOPER");
        } else if (statusName.equalsIgnoreCase("recruit")) {
            return roleRepository.findByName("ROLE_RECRUIT");
        }

        throw new IllegalArgumentException("Statut non reconnu : " + statusName);
    }
}
