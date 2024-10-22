package org.example.loginform.service;

import org.example.loginform.model.UserModel;
import org.example.loginform.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {

    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserModel registerUser(String login, String password, String email) {
        if (login == null || password == null) {
            return null;
        } else {

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            UserModel usersModel = new UserModel();
            usersModel.setEmail(email);
            usersModel.setLogin(login);
            usersModel.setPassword(hashedPassword);

            return userRepository.save(usersModel);
        }
    }

    public UserModel authenticated(String login, String password) {
        Optional<UserModel> userOptional = userRepository.findByLogin(login);

        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            if (BCrypt.checkpw(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
