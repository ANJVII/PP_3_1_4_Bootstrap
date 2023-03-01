package ru.kata.spring.boot_security.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.validation.CheckEmail;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    @NotBlank(message = "First name is required field")
    private String name;
    @Column(name = "last_name")
    @NotBlank(message = "Last name is required field")
    private String lastName;
    @Column(name = "department")
    private String department;
    @Column(name = "salary")
    private int salary;
    @Column(name = "email")
    @CheckEmail(value = "@mail.ru", message = "email must ends with @mail.ru")
    private String email;
    @Column(name = "password")
    @Size(min = 6, message = "password must be min 6 symbols")
    private String password;
    @Pattern(regexp = "\\d{3}-\\d{2}-\\d{2}", message = "please use pattern XXX-XX-XX")
    @Column(name = "phone_number")
    private String phoneNumber;
    @ManyToMany
    @JoinTable(name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public String getRole() {
        List<String> strRoles = roles.stream().map(Role::toString).collect(Collectors.toList());
        if (roles.size() == 2) {
            return strRoles.get(0).replaceFirst("ROLE_", "") + "\n"
                    + strRoles.get(1).replaceFirst("ROLE_", "");
        } else {
            return strRoles.get(0).replaceFirst("ROLE_", "");
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
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
