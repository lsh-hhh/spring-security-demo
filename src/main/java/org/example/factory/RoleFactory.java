package org.example.factory;

import org.example.model.Role;
import org.example.model.User;
import org.example.model.UserRole;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @ClassName RoleFactory
 * @Author lsh
 * @Date 2023/7/19 11:46
 */
public class RoleFactory {

    private final static String[] ROLE_NAME = new String[]{"普通用户", "管理员"};
    private final static String[] ROLE_CODE = new String[]{"user", "admin"};

    private static List<Role> ROLES;
    private static List<UserRole> USER_ROLES;
    static {
        initRoles();
        initUserRoles();
    }

    private static void initUserRoles() {
        List<User> users = UserFactory.getUsers();
        AtomicInteger adminCount = new AtomicInteger();
        Random random = new Random();
        USER_ROLES = users.stream()
                .map(u -> {
                    UserRole userRole;
                    Role role = ROLES.get(random.nextInt(ROLE_NAME.length));
                    if (Objects.equals("admin", role.getCode()) && adminCount.get() <= 5) {
                        adminCount.addAndGet(1);
                        userRole = new UserRole(u.getId(), role.getId());
                    } else {
                        userRole = new UserRole(u.getId(), 1L);
                    }
                    return userRole;
                })
                .collect(Collectors.toList());
    }

    private static void initRoles() {
        ROLES = IntStream.range(0, ROLE_NAME.length)
                .mapToObj(i -> Role.builder()
                        .id((long)(i + 1))
                        .roleName(ROLE_NAME[i])
                        .code(ROLE_CODE[i])
                        .build()
                )
                .collect(Collectors.toList());
    }

    public static List<Role> getRoles() {
        return ROLES;
    }

    public static Role getRoleByCode(String code) {
        return ROLES.stream()
                .filter(r -> Objects.equals(code, r.getCode()))
                .findFirst()
                .orElse(null);
    }

    public static Role getRoleById(Long id) {
        return ROLES.stream()
                .filter(r -> Objects.equals(id, r.getId()))
                .findFirst()
                .orElse(null);
    }

    public static List<UserRole> getUserRolesByUserId(Long userId) {
        return USER_ROLES.stream()
                .filter(ur -> Objects.equals(userId, ur.getUserId()))
                .collect(Collectors.toList());
    }

    public static List<UserRole> getUserRolesByRoleId(Long roleId) {
        return USER_ROLES.stream()
                .filter(ur -> Objects.equals(roleId, ur.getRoleId()))
                .collect(Collectors.toList());
    }
}
