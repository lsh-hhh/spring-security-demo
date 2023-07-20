package org.example.security;

import lombok.Data;
import org.example.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * @ClassName UserDetailsImpl
 * @Author lsh
 * @Date 2023/7/18 10:38
 */
@Data
public class UserDetailsImpl implements UserDetails {
    private User user;
    private List<String> permissions;
    private List<GrantedAuthority> authorities;

    public UserDetailsImpl(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (CollectionUtils.isEmpty(authorities)) {
            String[] strArr = new String[permissions.size()];
            authorities = AuthorityUtils.createAuthorityList(permissions.toArray(strArr));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
//            return "{noop}" + user.getPassword();
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
