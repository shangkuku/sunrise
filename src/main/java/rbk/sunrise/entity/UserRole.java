package rbk.sunrise.entity;

import javax.persistence.*;
import lombok.*;
import rbk.sunrise.base.Entity;

@Table(name = "rbk_user_role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRole extends Entity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "user_id")
    private Long userId;
}