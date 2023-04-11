package ru.stud.homer.springpreproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stud.homer.springpreproject.models.User;
import ru.stud.homer.springpreproject.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findUserById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Transactional
    public void add(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void update(Long id, User user) {
        User userToBeUpdated = findById(id);
        userToBeUpdated.setName(user.getName());
        userToBeUpdated.setEmail(user.getEmail());
        userToBeUpdated.setAge(user.getAge());
    }

    @Transactional
    public void delete(Long id) {
        userRepository.delete(userRepository.findUserById(id));
    }

}
