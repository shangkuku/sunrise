package rbk.sunrise.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity<PK extends Serializable> extends IdOnlyEntity<PK> {

    protected Date lastUpdateTime;

    protected Date createTime;

    protected int version = 0;
}
