package ro.msg.edu.jbugs.usermanagement.persistance.dao;

import ro.msg.edu.jbugs.usermanagement.persistance.entity.Role;
import ro.msg.edu.jbugs.usermanagement.persistance.entity.User;

import javax.ejb.Remote;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;


/**\
 * Provides functions to manage users enities.
 */
public interface UserPersistanceManagement extends Serializable {

    /**
     * Persists user into db.
     * @param user
     * @return the created user
     */
    User createUser(@NotNull User user);

    /**
     * Updates a user from the db
     * @param user
     * @return the newuser
     */
    User updateUser(@NotNull User user);

    /**
     * Return a List with all users
     * @return the list with user objects
     */
    List<User> getAllUsers();

    /**
     *
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    void createRole(Role role);

    void removeRole(Role role);

    Role updateRole(Role role);

    Role getRoleForId(long id);

    List<Role> getAllRoles();

    List<User>getUserByEmail(@NotNull String email);

    Optional<User> getUserByEmail2(@NotNull String email);

    List<String> getUsernamesLike(@NotNull String username);
}
