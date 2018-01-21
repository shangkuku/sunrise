package rbk.sunrise.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class IdOnlyEntity<PK extends Serializable> {
    protected PK id;
}
