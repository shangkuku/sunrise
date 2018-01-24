package rbk.sunrise.base;

import java.io.Serializable;


public abstract class IdOnlyEntity<PK extends Serializable> {
    protected abstract PK getId();
}
