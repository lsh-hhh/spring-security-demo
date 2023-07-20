package org.example.model;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName Role
 * @Author lsh
 * @Date 2023/7/19 11:48
 */
@Data
@Builder
public class Menu {

    private Long id;

    private String name;
    // 路由地址
    private String path;
    // 组件路径
    private String component;
    // 菜单权限标识
    private String perms;
}
