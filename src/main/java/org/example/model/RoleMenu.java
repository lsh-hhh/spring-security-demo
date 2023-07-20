package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.factory.MenuFactory;
import org.example.factory.RoleFactory;

import java.util.Objects;

/**
 * @ClassName RoleMenu
 * @Author lsh
 * @Date 2023/7/19 11:55
 */
@Data
@AllArgsConstructor
public class RoleMenu {

    private Long roleId;

    private Long menuId;

    public Role getRole() {
        Role role = RoleFactory.getRoleById(roleId);
        if (Objects.isNull(role)) {
            throw new RuntimeException("角色不存在");
        }
        return role;
    }

    public Menu getMenu() {
        Menu menu = MenuFactory.getMenuById(menuId);
        if (Objects.isNull(menu)) {
            throw new RuntimeException("菜单不存在");
        }
        return menu;
    }
}
