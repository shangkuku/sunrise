package rbk.sunrise.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rbk.sunrise.base.BaseController;
import rbk.sunrise.entity.User;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User, Long> {
}