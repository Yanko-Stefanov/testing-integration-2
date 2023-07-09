package jwt.token.demo.repository;

import jwt.token.demo.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
