package org.example.factory;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.example.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @ClassName UserFactory
 * @Author lsh
 * @Date 2023/7/17 11:58
 */
public class UserFactory {

    private static final List<User> userList;

    private static final String[] FIRST_NAME = new String[]{"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "楚"};
    private static final String[] LAST_NAME = new String[]{"魏", "蒋", "史", "黄", "梁", "关", "张", "马", "于", "姜", "刘"};

    static {
        Random random = new Random();
        HanyuPinyinOutputFormat hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
        Set<String> createdNames = new HashSet<>();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userList = IntStream.range(0, 100)
                .mapToObj(i -> {
                    String name = FIRST_NAME[random.nextInt(FIRST_NAME.length)] + LAST_NAME[random.nextInt(LAST_NAME.length)];
                    while (createdNames.contains(name)) {
                        name = FIRST_NAME[random.nextInt(FIRST_NAME.length)] + LAST_NAME[random.nextInt(LAST_NAME.length)];
                    }
                    createdNames.add(name);
                    try {
                        return User.builder()
                                .id((long) i)
                                .username(PinyinHelper.toHanYuPinyinString(name, hanyuPinyinOutputFormat, "", true))
                                .name(name)
                                .ipAddress("192.168.1." + (i + 100))
                                .address("福建厦门")
                                .gender(i % 2)
                                .password(passwordEncoder.encode("123"))
                                .build();
                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        userList.forEach(System.out::println);
    }

    public static List<User> getUsers() {
        return userList;
    }

    public static User getUserByUsername(String username) {
        return userList.stream()
                .filter(u -> Objects.equals(u.getUsername(), username))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }

    public static User getUserById(Long id) {
        return userList.stream()
                .filter(u -> Objects.equals(u.getId(), id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }
}
