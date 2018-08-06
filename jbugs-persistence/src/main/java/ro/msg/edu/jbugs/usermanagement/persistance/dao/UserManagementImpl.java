package ro.msg.edu.jbugs.usermanagement.persistance.dao;

import ro.msg.edu.jbugs.usermanagement.persistance.entity.Role;
import ro.msg.edu.jbugs.usermanagement.persistance.entity.User;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Stateless
public class UserManagementImpl /*implements Serializable */{

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager em;

    /**
     * Persists a new user into the DB.
     * @param user - user to persist
     * @return the User entitz that was persisted.
     */
    public User createUser(@NotNull User user) {
         em.persist(user);
         em.flush();
         return user;
    }

    /**
     * Updates an existing user in the db
     * @param user - The user with modofied data.
     * @return the user with the new modifications.
     */
    public User updateUser(@NotNull User user) {
        return em.merge(user);
    }

    /**
     * Retrive all users from the database
     * @return a list with the retrieved user objects
     */
    public List<User> getAllUsers(){
        return em.createNamedQuery(User.GET_ALL_USERS, User.class).getResultList();
    }

    /**
     * Retrieve a user by it's username from the DB.
     * @param username - the username for the search criteria
     * @return an Optional containing the user object or an empty optional if
     *  the query didn't return any result.
     */
    public Optional<User> getUserByUsername(@NotNull String username) {
        TypedQuery<User> q = em.createQuery(User.GET_USER_BY_USERNAME, User.class);
        q.setParameter("username",username);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * Persists a role into the DB
     * @param role - the role to be persisted.
     */
    public void createRole(@NotNull Role role) {
        em.persist(role);
    }

    /**
     * Removes a role from the db.
     * @param role - the role to be removed
     */
    public void removeRole(Role role) {
        em.remove(role);

    }

    /**
     * Modifies a role in the database
     * @param role - the role to be modified with the new values
     * @return - the modified role object
     */
    public Role updateRole(Role role) {
        em.merge(role);
        return role;
    }

    /**
     * Retrieve a role object by it's id
     * @param id - Id for search criteria
     * @return A role object
     */
    public Role getRoleById(long id) {
        Query q = em.createQuery("SELECT r FROM Role r WHERE r.id=" + id);
        return (Role) q.getSingleResult();
    }

    /**
     * Retrieves all roles from the db.
     * @return a list of role objects
     */
    public List<Role> getAllRoles() {
        TypedQuery<Role> q = em.createNamedQuery(Role.GET_ALL_ROLES, Role.class);
        return q.getResultList();

    }

    /**
     * Searches and returns a user by it's email
     * @param email - email for search criteria
     * @return An optional containing the user object or an empty optional if the query returned no result.
     */
    public Optional<User> getUserByEmail(String email) {

        TypedQuery<User> q = em.createNamedQuery(User.GET_USER_BY_EMAIL, User.class);
        q.setParameter("email",email);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    /**
     * Retrieve all usernames that are similar to the parameter string.
     * @param username - parameter for search criteria
     * @return the usernames as a list of strings
     */
    public List<String> getUsernamesLike(String username) {
        Query q = em.createNamedQuery(User.GET_USERNAMES_LIKE, String.class);
        q.setParameter("username", username+"%");
        return q.getResultList();
    }
}
