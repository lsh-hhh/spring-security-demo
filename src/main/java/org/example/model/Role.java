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
public class Role {

    private Long id;

    private String roleName;
    // 权限code
    private String code;
}
