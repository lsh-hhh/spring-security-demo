package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName User
 * @Author lsh
 * @Date 2023/7/17 10:52
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String username;

    private String ipAddress;

    private String password;

    private String name;

    private String address;

    private Integer gender;
}
