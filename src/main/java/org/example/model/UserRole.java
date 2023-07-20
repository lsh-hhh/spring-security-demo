package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.factory.RoleFactory;
import org.example.factory.UserFactory;

/**
 * @ClassName UserRole
 * @Author lsh
 * @Date 2023/7/19 11:55
 */
@Data
@AllArgsConstructor
public class UserRole {

    private Long userId;

    private Long roleId;

    public User getUser() {
        return UserFactory.getUserById(userId);
    }

    public Role getRole() {
        return RoleFactory.getRoleById(roleId);
    }
}
