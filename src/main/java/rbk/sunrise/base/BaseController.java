package rbk.sunrise.base;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rbk.sunrise.validation.Group;

import java.io.Serializable;

public abstract class BaseController<T extends IdOnlyEntity, PK extends Serializable> {

    @Autowired
    BaseService<T, PK> baseService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<T> get(@PathVariable("id") PK id) {
        T t =  baseService.selectByPrimaryKey(id);
        if (t == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<T> save(@Validated(Group.New.class) @RequestBody T t) {
        baseService.insertSelective(t);
        return new ResponseEntity<>(t, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<T> put(@Validated(Group.Existing.class) @RequestBody T t) {
        baseService.updateByPrimaryKey(t);
        return new ResponseEntity<>(t, HttpStatus.CREATED);
    }
    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<T> patch(@Validated(Group.Existing.class) @RequestBody T t) {
        baseService.updateByPrimaryKeySelective(t);
        return new ResponseEntity<>(t, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<T> delete(@PathVariable("id") PK id) {
        baseService.deleteByPrimaryKey(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
