package com.example.entity;

import com.example.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@ToString
@Entity
@Table(
        name = "tbl_user",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "user_email_unique",
                        columnNames = "email"
                )
        }
)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "user_id_seq",
            sequenceName = "user_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_seq"
    )
    @Column(name = "user_id")
    long id;

    @Column(
            name = "first_name",
            nullable = false
    )
    String firstName;

    @Column(
            name = "last_name",
            nullable = false
    )
    String lastName;

    @Column(
            nullable = false
    )
    String email;

    @Column(
            nullable = false
    )
    private Integer age;

    @Column(
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(
            nullable = false
    )
    String password;

    @Column(
            nullable = false
    )
    String bvn;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "fk_account_id",
            referencedColumnName = "account_id"
    )
    Account account;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private List<Transaction> transactions;

    @CreationTimestamp
    @Column(
            name="created_at",
            updatable = false
    )
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    LocalDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
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
