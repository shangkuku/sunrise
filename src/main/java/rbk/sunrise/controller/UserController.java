package rbk.sunrise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import rbk.sunrise.base.BaseController;
import rbk.sunrise.entity.User;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController<User, Long> {
}