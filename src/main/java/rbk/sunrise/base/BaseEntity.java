package rbk.sunrise.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity<PK extends Serializable> extends IdOnlyEntity<PK> {

    protected Date lastUpdateTime = new Date();

    protected Date createTime = new Date();

    protected int version = 0;
}
