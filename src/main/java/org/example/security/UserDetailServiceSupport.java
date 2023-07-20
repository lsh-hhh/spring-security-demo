package org.example.security;

import org.example.factory.UserFactory;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserDetailServiceSupport
 * @Author lsh
 * @Date 2023/7/17 15:17
 */
@Service
public class UserDetailServiceSupport implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = UserFactory.getUserByUsername(username);
        List<String> permissions = userService.getPerms(user.getId());
        return new UserDetailsImpl(user, permissions);
    }
}
