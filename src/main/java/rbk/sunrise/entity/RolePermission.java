package rbk.sunrise.entity;

import javax.persistence.*;
import lombok.*;
import rbk.sunrise.base.Entity;

@Table(name = "rbk_role_permission")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission extends Entity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_id")
    private Long roleId;

    private Integer permission;

    private String module;
}