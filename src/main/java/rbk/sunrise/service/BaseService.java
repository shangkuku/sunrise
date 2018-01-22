package rbk.sunrise.service;

import org.springframework.beans.factory.annotation.Autowired;
import rbk.sunrise.dao.BaseMapper;
import rbk.sunrise.entity.IdOnlyEntity;

import java.io.Serializable;

public class BaseService<T extends IdOnlyEntity, PK extends Serializable> {

    @Autowired
    BaseMapper<T> baseMapper;

    public T get(PK pk) {
        return null;
    }

}
