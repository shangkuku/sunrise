package rbk.sunrise.entity;

import java.io.Serializable;


public abstract class IdOnlyEntity<PK extends Serializable> {
    abstract PK getId();
}
