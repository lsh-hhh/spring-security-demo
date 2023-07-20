package org.example.service;

import org.example.factory.MenuFactory;
import org.example.factory.RoleFactory;
import org.example.model.User;
import org.example.security.UserDetailsImpl;
import org.example.vo.UserVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName UserService
 * @Author lsh
 * @Date 2023/7/18 17:14
 */
@Service
public class UserService {

    public UserVO getInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        return new UserVO(user);
    }

    public List<String> getPerms(Long userId) {
        return RoleFactory.getUserRolesByUserId(userId).stream()
                .map(ur -> MenuFactory.getRoleMenusByRoleId(ur.getRoleId())
                        .stream()
                        .map(rm -> rm.getMenu().getPerms())
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
