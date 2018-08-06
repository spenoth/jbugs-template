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
     * Find the user with the username and set it's status to false
     * @param username user's username for the search criteria
     */
    void deactivateUser(String username) throws BuisnissException;

    /**
     * Find the user with the username and set it's status to true
     * @param username user's username for the search criteria
     */
    void activateUser(String username) throws BuisnissException;

    /**
     * Retrieve all users.
     * @return a list of users
     */
    List<UserDTO> getAllUsers();

    /**
     * Login the user with the provided credentials
     * @param username - usarname to login with
     * @param password - password to login with
     * @return the user on succsessfull login. Throw an exception on onvalid username or password
     */
    UserDTO login(String username, String password) throws BuisnissException;

}
