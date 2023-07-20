package org.example.factory;

import org.example.model.Menu;
import org.example.model.Role;
import org.example.model.RoleMenu;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @ClassName RoleFactory
 * @Author lsh
 * @Date 2023/7/19 11:46
 */
public class MenuFactory {


    private final static String[] MENU_NAME = new String[]{"定时任务", "环境变量", "配置文件", "脚本管理", "依赖管理", "系统设置"};
    private final static String[] MENU_CODE = new String[]{"schedule", "evn", "config", "script", "dependency", "settings"};
    private final static String[] OPERATE_CODE = new String[]{"add", "delete", "edit", "list"};

    private static List<Menu> MENUS;
    private static List<RoleMenu> ROLE_MENUS;
    private final static Map<String, List<String>> permMap = new HashMap<>();

    static {
        initPermMap();
        initMenus();
        initRoleMenu();
    }

    private static void initRoleMenu() {
        List<Role> roles = RoleFactory.getRoles();
        ROLE_MENUS = roles.stream()
                .map(r -> {
                    boolean admin = r.getCode().equals("admin");
                    return MENUS.stream()
                            .map(m -> {
                                RoleMenu rm = null;
                                if (!admin && m.getPerms().contains("list")) {
                                    rm = new RoleMenu(r.getId(), m.getId());
                                }
                                if (admin) {
                                    rm = new RoleMenu(r.getId(), m.getId());
                                }
                                return rm;
                            })
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
//        System.out.println(ROLE_MENUS);
    }

    private static void initMenus() {
        MENUS = IntStream.range(0, MENU_NAME.length * 4)
                .mapToObj(i -> {
                    String menuCode = MENU_CODE[i / OPERATE_CODE.length];
                    List<String> strings = permMap.get(menuCode);
                    String perms = strings.get(i % OPERATE_CODE.length);
                    return Menu.builder()
                            .id((long) i + 1)
                            .name(MENU_NAME[i / OPERATE_CODE.length])
                            .perms(perms)
                            .path(menuCode)
                            .component(perms.replace(":", "/"))
                            .build();
                })
                .collect(Collectors.toList());
    }

    private static void initPermMap() {
        IntStream.range(0, MENU_NAME.length)
                .forEach(i -> {
                    String s = MENU_CODE[i];
                    List<String> collect = Arrays.stream(OPERATE_CODE)
                            .map(c -> s + ":" + c)
                            .collect(Collectors.toList());
                    permMap.put(s, collect);
                });
//        System.out.println(permMap);
    }

    public static List<Menu> getMenus() {
        return MENUS;
    }

    public static Menu getMenuById(Long menuId) {
        return MENUS.get(menuId.intValue() - 1);
    }

    public static List<RoleMenu> getRoleMenus() {
        return ROLE_MENUS;
    }

    public static List<RoleMenu> getRoleMenusByRoleId(Long roleId) {
        return ROLE_MENUS.stream()
                .filter(rm -> rm.getRoleId().equals(roleId))
                .collect(Collectors.toList());
    }

    public static List<RoleMenu> getRoleMenusByMenuId(Long menuId) {
        return ROLE_MENUS.stream()
                .filter(rm -> rm.getMenuId().equals(menuId))
                .collect(Collectors.toList());
    }

    public static RoleMenu getRoleMenu(Long roleId, Long menuId) {
        return ROLE_MENUS.stream()
                .filter(rm -> rm.getRoleId().equals(roleId))
                .filter(rm -> rm.getMenuId().equals(menuId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("无此权限配置"));
    }

    public static void main(String[] args) {
        RoleMenu roleMenu = getRoleMenu(2L, 1L);

        System.out.println(roleMenu.getMenu());
        System.out.println(roleMenu.getRole());
    }
}
