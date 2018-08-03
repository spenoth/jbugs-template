package ro.msg.edu.jbugs.usermanagement.business.control;

import ro.msg.edu.jbugs.usermanagement.business.exception.BuisnissException;
import ro.msg.edu.jbugs.usermanagement.business.dto.UserDTO;
import java.util.List;

public interface UserManagement {

    /**
     * Method is used for persisting the User from an UserDTO
     * Generates username and does the validations.
     * @param userDTO - user information
     * @return the newly crated user entity as a DTO
     */
    UserDTO createUser(UserDTO userDTO) throws BuisnissException;

    /**
     *
     * @param username
     */
    void deactivateUser(String username);

    /**
     *
     * @param username
     */
    void activateUser(String username);

    /**
     *
     * @return
     */
    List<UserDTO> getAllUsers();

    /**
     *
     * @param username
     * @param password
     * @return
     */
    UserDTO login(String username, String password) throws BuisnissException;

}
