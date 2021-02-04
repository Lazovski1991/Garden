package my.company.service.User;

import my.company.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getUserId(Integer userId);

    void add(User user);
}
