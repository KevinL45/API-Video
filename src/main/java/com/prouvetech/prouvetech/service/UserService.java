package com.prouvetech.prouvetech.service;

import com.prouvetech.prouvetech.config.JwtTokenProvider;
import com.prouvetech.prouvetech.model.Role;
import com.prouvetech.prouvetech.model.User;
import com.prouvetech.prouvetech.repository.UserRepository;
import com.prouvetech.prouvetech.utils.ResponseMessage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    public ResponseMessage authenticateUser(String email, String password) {

        // Vérifier que l'email et le mot de passe ne sont pas vides
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return new ResponseMessage(false,
                    "Email ou mot de passe ne doivent pas être vides.");
        }

        // Chercher l'utilisateur par email
        User user = userRepository.findByMail(email);
        if (user == null) {
            return new ResponseMessage(false, "Identifiants invalides");
        }

        // Vérifier que le mot de passe correspond
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return new ResponseMessage(false, "Identifiants invalides");
        }

        // Créer un token JWT si tout est OK
        String token = jwtTokenProvider.createToken(user.getMail());
        return new ResponseMessage(true, token);
    }

    public ResponseMessage createUser(User user) {

        User emailExist = userRepository.findByMail(user.getMail());

        if (emailExist != null)
            return new ResponseMessage(false, "Vous avez déja un compte");

        user.setPassword(hashPassword(user.getPassword()));
        Role roleToAssign = new Role();
        roleToAssign = roleService.getRoleByStatus(user.getStatus()).get();

        user.setRoles(List.of(roleToAssign));
        userRepository.save(user);
        return new ResponseMessage(true, "Vous êtes inscrit. Votre rôle est \"" + roleToAssign.getName() +
                "\", et votre statut est \"" + user.getStatus().getName() + "\".");
    }

    public ResponseMessage updateUser(User user) {

        userRepository.save(user);

        return new ResponseMessage(true,
                "Votre compte a été modifié, Voici votre role de '"
                        + roleService.getRoleByStatus(user.getStatus()).get().getName()
                        + "'', votre statut est '" + user.getStatus().getName() + "'");

    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Aucun utilisateur"));
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        User user = userRepository.findByMail(mail);
        return user;
    }

    public User getUserByMail(String mail) {

        User user = userRepository.findByMail(mail);
        return user;
    }

    public List<User> searchUser(String title, Long statusId) {
        if (title != null && statusId != null) {
            return userRepository.findByTitleContainingIgnoreCaseAndStatusId(title, statusId);
        } else if (title != null) {
            return userRepository.findByTitleContainingIgnoreCase(title);
        } else if (statusId != null) {
            return userRepository.findByStatusId(statusId);
        } else {
            return userRepository.findAll();
        }
    }

}
