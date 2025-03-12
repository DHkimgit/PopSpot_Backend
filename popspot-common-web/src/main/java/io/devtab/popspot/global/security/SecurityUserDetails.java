package io.devtab.popspot.global.security;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.devtab.popspot.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityUserDetails implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean accountNonLocked;
    @JsonIgnore
    private boolean enabled;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private boolean credentialsNonExpired;
    @JsonIgnore
    private boolean accountNonExpired;

    @Builder
    private SecurityUserDetails(Integer userId, String username, Collection<? extends GrantedAuthority> authorities, boolean accountNonLocked) {
        this.userId = userId;
        this.username = username;
        this.authorities = authorities;
        this.accountNonLocked = accountNonLocked;
    }

    public static UserDetails from(User user) {
        return builder()
            .userId(user.getId())
            .username(user.getEmail())
            .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.getUserType().name())))
            .accountNonLocked(user.getIsDeleted())
            .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEnabled() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "SecurityUserDetails{" +
            "id=" + userId +
            ", username='" + username + '\'' +
            ", authorities=" + authorities +
            ", accountNonLocked=" + accountNonLocked +
            '}';
    }
}
