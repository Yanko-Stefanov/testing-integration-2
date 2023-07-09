package jwt.token.demo.controller;

import jwt.token.demo.model.RoleModel;
import jwt.token.demo.service.RoleServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {

    private final RoleServiceImpl service;

    public RoleController(RoleServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/roles")
    public RoleModel createRole(@RequestBody RoleModel roleEntity) {
        return service.createRole(roleEntity);
    }

    @GetMapping("/roles")
    public List<RoleModel> getAllRoles() {
        return service.getAllRoles();
    }

    @DeleteMapping("/roles/{roleId}")
    public void deleteRole(@PathVariable Long roleId) {
        service.deleteRoleById(roleId);
    }
}
