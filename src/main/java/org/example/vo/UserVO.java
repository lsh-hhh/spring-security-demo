package org.example.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.User;

/**
 * @ClassName User
 * @Author lsh
 * @Date 2023/7/17 10:52
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    private Long id;

    private String username;

    private String ipAddress;

    private String name;

    private String address;

    private Integer gender;

    public UserVO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.gender = user.getGender();
        this.address = user.getAddress();
        this.ipAddress = user.getIpAddress();
    }
}
