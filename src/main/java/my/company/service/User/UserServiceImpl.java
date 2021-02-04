package my.company.service.User;

import my.company.entity.User;
import my.company.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserId(Integer userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    @Transactional
    @Override
    public void add(User user) {
        userRepository.save(user);
    }
}
