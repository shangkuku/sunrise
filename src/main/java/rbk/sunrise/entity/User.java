package rbk.sunrise.entity;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import rbk.sunrise.base.Entity;
import rbk.sunrise.validation.Group;

@Table(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends Entity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(groups = Group.New.class)
    @NotNull(groups = Group.Existing.class)
    private Long id;

    @NotNull
    private String name;

    private Integer sex;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    private Integer version;
}