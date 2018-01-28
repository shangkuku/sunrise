package rbk.sunrise.base;

import java.io.Serializable;


/**
 * 因为DAO层不需要主键泛型，这边先预留
 * @param <PK>
 */
public abstract class Entity<PK extends Serializable> {

}
