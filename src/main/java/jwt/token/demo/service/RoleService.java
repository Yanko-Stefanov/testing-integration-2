package jwt.token.demo.service;

import jwt.token.demo.entity.RoleEntity;
import jwt.token.demo.model.RoleModel;

import java.util.List;

public interface RoleService {

    public RoleModel createRole(RoleModel roleEntity);
    public List<RoleModel> getAllRoles();
    public RoleModel getRoleById(Long roleId);
    public void deleteRoleById(Long roleId);
}
