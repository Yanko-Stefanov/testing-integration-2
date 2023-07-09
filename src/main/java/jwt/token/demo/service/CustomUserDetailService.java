package jwt.token.demo.service;


import jwt.token.demo.entity.RoleEntity;
import jwt.token.demo.entity.UserEntity;
import jwt.token.demo.model.RoleModel;
import jwt.token.demo.model.UserModel;
import jwt.token.demo.repository.RoleRepository;
import jwt.token.demo.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;


    public UserModel register(UserModel userModel) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userModel, userEntity);

        Set<RoleEntity> roleEntities = new HashSet<>();
        for (RoleModel rm : userModel.getRoles()) {
            Optional<RoleEntity> optRole = roleRepository.findById(rm.getId());
            if (optRole.isPresent()) {
                roleEntities.add(optRole.get());
            }
        }
        userEntity.setRoles(roleEntities);
        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        userEntity = userRepository.save(userEntity);

        BeanUtils.copyProperties(userEntity, userModel);

        Set<RoleModel> roleModels = new HashSet<>();
        RoleModel rm = null;
        for (RoleEntity re : userEntity.getRoles()) {
            rm = new RoleModel();
            rm.setId(re.getId());
            rm.setRoleName(re.getRoleName());
            roleModels.add(rm);
        }
        userModel.setRoles(roleModels);

        return userModel;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User does not exist");
        }

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userEntity, userModel);

        Set<RoleModel> roleModels = new HashSet<>();
        RoleModel rm = null;
        for (RoleEntity re : userEntity.getRoles()) {
            rm = new RoleModel();
            rm.setId(re.getId());
            rm.setRoleName(re.getRoleName());
            roleModels.add(rm);
        }
        userModel.setRoles(roleModels);

        return userModel;
    }
}
