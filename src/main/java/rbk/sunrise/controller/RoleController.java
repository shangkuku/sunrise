package rbk.sunrise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import rbk.sunrise.base.BaseController;
import rbk.sunrise.entity.Role;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController<Role, Long> {
}