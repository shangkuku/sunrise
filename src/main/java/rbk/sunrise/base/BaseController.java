package rbk.sunrise.base;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rbk.sunrise.validation.Group;

import java.io.Serializable;
import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
public abstract class BaseController<T extends Entity, PK extends Serializable> {

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

    @RequestMapping(method = RequestMethod.GET)
    protected ResponseEntity<List<T>> find(@ModelAttribute T t) {
        List<T> list = baseService.select(t);
        if (list == null || list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ResponseEntity<T> save(@Validated(Group.New.class) @RequestBody T t) {
        baseService.insert(t);
        return new ResponseEntity<>(t, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    protected ResponseEntity<T> put(@Validated(Group.Existing.class) @RequestBody T t) {
        baseService.updateByPrimaryKey(t);
        return new ResponseEntity<>(t, HttpStatus.CREATED);
    }
    @RequestMapping(method = RequestMethod.PATCH)
    protected ResponseEntity<T> patch(@Validated(Group.Existing.class) @RequestBody T t) {
        baseService.updateByPrimaryKeySelective(t);
        return new ResponseEntity<>(t, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    protected ResponseEntity<T> delete(@PathVariable("id") PK id) {
        baseService.deleteByPrimaryKey(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
