package rbk.sunrise.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rbk.sunrise.entity.IdOnlyEntity;
import rbk.sunrise.service.BaseService;

import java.io.Serializable;

public class BaseController<T extends IdOnlyEntity, PK extends Serializable> {

    @Autowired
    BaseService<T, PK> baseService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<T> get(@PathVariable("id") PK id) {
        T t =  baseService.get(id);
        return new ResponseEntity<>(t, HttpStatus.OK);
    }
}
