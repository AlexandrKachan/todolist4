package by.lex.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import by.lex.todolist.persist.entity.User;
import by.lex.todolist.persist.repo.UserRepository;
import by.lex.todolist.model.UserModel;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void create(UserModel userModel) {
        User user = new User();
        user.setUsername(userModel.getUsername());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userRepository.save(user);
    }
}
