package rbk.sunrise.entity;

import javax.persistence.*;
import lombok.*;
import rbk.sunrise.base.Entity;

@Table(name = "rbk_role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends Entity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;
}