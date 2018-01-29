package rbk.sunrise.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.*;
import rbk.sunrise.base.Entity;

@Table(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends Entity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    @Column(name = "create_time")
    private Date createTime;

    private String salt;

    @Column(name = "mobile_phone")
    private String mobilePhone;
}