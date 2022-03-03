package com.devtb.crmapp.service;


import com.devtb.crmapp.domain.Role;
import com.devtb.crmapp.domain.User;
import com.devtb.crmapp.repository.UserRepository;
import com.devtb.crmapp.service.DTOS.UserRequestDTO;
import com.devtb.crmapp.service.DTOS.UserResponseDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    /**
     * method for creation of default users on application startup
     * users available:admin/admin,role ADMIN - user/user, role USER
     *
     * @throws Exception
     */
    public void createDefaultUsers() throws Exception {
        try {
            if (userRepository.findByUserName("admin").isEmpty()) {
                Optional<Role> roles = roleService.findByRoleName("ADMIN");
                User appUser = createDefaultUser(List.of(roles.get()), "admin", "admin", "$2a$10$IqTJTjn39IU5.7sSCDQxzu3xug6z/LPU6IF0azE/8CkHCwYEnwBX.");
                userRepository.save(appUser);
            }
            if (userRepository.findByUserName("user").isEmpty()) {
                Optional<Role> roles = roleService.findByRoleName("USER");

                User appUser = createDefaultUser(List.of(roles.get()), "user", "user", "$2a$12$LUxev85LH3TjBDBCmuKPn.NlFZYbTLOKOttb7RNCUOkoF4/ujFPmS");
                userRepository.save(appUser);
            }
        } catch (Exception e) {
            throw new Exception(String.format("Error on creation of default users - %s", e.getMessage()));
        }
    }


    public Optional<User> findById(Long creationUserId) {
        return userRepository.findById(creationUserId);
    }


    /**
     * method for creation of new User in DB. all fields of UserRequestDTO are required
     * to create a new user.
     *
     * @param userDTO
     * @throws Exception
     */
    public void createNewUser(UserRequestDTO userDTO) throws Exception {

        validate(userDTO, true);
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setFullName(userDTO.getFullName());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        user.setRoles(roleService.getValidRoles(userDTO.getRoleIds()));
        userRepository.save(user);
    }


    public UserResponseDTO getUserById(Long userId) throws Exception {
        Optional<User> user = findUserById(userId);
        return user.get().toUserResponseDTO();
    }

    /**
     * method that returs a list of UsersResponseDTO.
     *
     * @return
     */
    public List<UserResponseDTO> getAllUser() {
        return userRepository.findAll().
                stream()
                .map(User::toUserResponseDTO).
                collect(Collectors.toList());
    }

    /**
     * Method that update an existent user
     * It's possible to update only fullName, password and roles
     * With is method is also possible to modify only user's role(e.g. change admin status)
     *
     * @param userDTO
     * @throws Exception
     */
    public void updateUser(UserRequestDTO userDTO) throws Exception {
        validate(userDTO, false);
        Optional<User> user = findUserById(userDTO.getUserId());

        User u = user.get();

        if (userDTO.getFullName()!=null && !userDTO.getFullName().trim().isEmpty())
            u.setFullName(userDTO.getFullName());

        if (userDTO.getPassword()!=null && !userDTO.getPassword().trim().isEmpty())
            u.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));

        if (userDTO.getRoleIds()!=null &&  !userDTO.getRoleIds().isEmpty())
            u.setRoles(roleService.getValidRoles(userDTO.getRoleIds()));
        userRepository.save(u);

    }

    public void deleteUser(Long userId) throws Exception {
        findUserById(userId);
        userRepository.deleteById(userId);
    }

    private Optional<User> findUserById(Long userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new Exception("User not found");
        return user;
    }

    private User createDefaultUser(List<Role> rolesList, String username, String fullName, String password) {
        User appUser = new User();
        appUser.setUserName(username);
        appUser.setFullName(fullName);
        appUser.setPassword(password);
        appUser.setRoles(rolesList);
        return appUser;
    }


    /**
     * method to validate an UserRequestDTO, in case of insert or update.
     * Check also if an username is already present, in case of creation of a nee user.
     *
     * @param userDTO
     * @param creation
     * @throws Exception
     */
    private void validate(UserRequestDTO userDTO, boolean creation) throws Exception {

        if (creation && (userDTO.getUserName() == null || userDTO.getUserName().trim().isEmpty()))
            throw new Exception("UserName is required");

        if (creation && userNameAlreadyExist(userDTO.getUserName()))
            throw new Exception("this userName already exists");

        if (creation && (userDTO.getPassword() == null || userDTO.getPassword().trim().isEmpty()))
            throw new Exception("Password is required");

        if (creation && (userDTO.getFullName() == null || userDTO.getFullName().trim().isEmpty()))
            throw new Exception("FullName is required");

        if (creation && (userDTO.getRoleIds()==null ||  userDTO.getRoleIds().isEmpty()))
            throw new Exception("Roles are required");

        if (!creation && userDTO.getUserId() == null)
            throw new Exception("UserId is required");
    }

    private boolean userNameAlreadyExist(String userName) {
        return userRepository.countByUserName(userName) > 0;
    }
}
