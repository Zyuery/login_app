package org.example.login_app.service;

import org.example.login_app.domain.User;

public interface UserService {
    User loginService(String uname, String password);
    User registService(User user);
    User emailLoginService(String email);
}
