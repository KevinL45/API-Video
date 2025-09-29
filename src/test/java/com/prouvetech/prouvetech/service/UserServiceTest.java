package com.prouvetech.prouvetech.service;

import com.prouvetech.prouvetech.model.Role;
import com.prouvetech.prouvetech.model.Status;
import com.prouvetech.prouvetech.model.User;
import com.prouvetech.prouvetech.repository.UserRepository;
import com.prouvetech.prouvetech.utils.ResponseMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInscriptionUtilisateur_Succes() {

        User newUser = new User();
        newUser.setFirstname("Jean");
        newUser.setLastname("Dupont");
        newUser.setMail("jean.dupont@email.com");
        newUser.setPassword("motdepasse123");

        Status status = new Status();
        status.setName("Développeur");
        newUser.setStatus(status);

        Role role = new Role();
        role.setName("ROLE_USER");

        when(userRepository.findByMail("jean.dupont@email.com")).thenReturn(null);

        when(roleService.getRoleByStatus(status)).thenReturn(Optional.of(role));

        when(userRepository.save(any(User.class))).thenReturn(newUser);

        ResponseMessage resultat = userService.createUser(newUser);

        assertTrue(resultat.isSuccess());
        assertTrue(resultat.getMessage().contains("Vous êtes inscrit"));

        verify(userRepository, times(1)).findByMail("jean.dupont@email.com");
        verify(userRepository, times(1)).save(any(User.class));
        verify(roleService, times(1)).getRoleByStatus(status);
    }

    @Test
    void testInscriptionUtilisateur_EmailDejaExistant() {
        User newUser = new User();
        newUser.setMail("jean.dupont@email.com");
        newUser.setPassword("motdepasse123");

        Status status = new Status();
        newUser.setStatus(status);

        User userExist = new User();
        userExist.setMail("jean.dupont@email.com");

        when(userRepository.findByMail("jean.dupont@email.com")).thenReturn(userExist);

        ResponseMessage resultat = userService.createUser(newUser);

        assertFalse(resultat.isSuccess());
        assertEquals("Vous avez déja un compte", resultat.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }
}