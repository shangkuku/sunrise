package rbk.sunrise.entity;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import rbk.sunrise.base.Entity;
import rbk.sunrise.validation.Group;

@Table(name = "rbk_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends Entity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Group.Existing.class)
    @Null(groups = Group.New.class)
    private Long id;

    private String name;

    @JsonIgnore
    private String password;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "last_password_reset_date")
    private Date lastPasswordResetDate;

}