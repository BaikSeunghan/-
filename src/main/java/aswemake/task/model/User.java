package aswemake.task.model;

import aswemake.task.base.Timestamped;
import aswemake.task.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.lang.reflect.Member;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "awm_user")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(String name, String password, UserRole role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }
}
