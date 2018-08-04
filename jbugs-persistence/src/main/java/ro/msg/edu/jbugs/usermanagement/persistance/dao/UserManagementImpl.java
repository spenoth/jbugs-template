package ro.msg.edu.jbugs.usermanagement.persistance.dao;

import ro.msg.edu.jbugs.usermanagement.persistance.entity.Role;
import ro.msg.edu.jbugs.usermanagement.persistance.entity.User;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Stateless
public class UserManagementImpl implements UserPersistanceManagement {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager em;

    @Override
    public User createUser(User user) {
         em.persist(user);
         em.flush();
         return user;
    }

    @Override
    public User updateUser(@NotNull User user) {
        return em.merge(user);
    }

    @Override
    public List<User> getAllUsers(){
        Query q = em.createQuery("SELECT u FROM User u");
        return q.getResultList();
    }

    @Override
    public User getUserByUsername(@NotNull String username) {
        Query q = em.createQuery("SELECT u FROM User u WHERE u.username='"
            + username + "'");
        try {
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void createRole(Role role) {
        em.persist(role);
    }

    @Override
    public void removeRole(Role role) {
        em.remove(role);

    }

    @Override
    public Role updateRole(Role role) {
        em.merge(role);
        return role;
    }

    @Override
    public Role getRoleForId(long id) {
        Query q = em.createQuery("SELECT r FROM Role r WHERE r.id=" + id);
        return (Role) q.getSingleResult();
    }

    @Override
    public List<Role> getAllRoles() {
        Query q = em.createQuery("SELECT r FROM Role r");
        return q.getResultList();
    }

    @Override
    public List<User> getUserByEmail(String email) {
        Query q = em.createQuery("SELECT U FROM User u WHERE u.email='"+email+"'");
        return  q.getResultList();
    }

    @Override
    public Optional<User> getUserByEmail2(String email) {

        TypedQuery<User> q = em.createQuery("SELECT u from User WHERE u.email = '" + email + "'", User.class);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<String> getUsernamesLike(String username) {
        Query q = em.createQuery("SELECT U.username FROM User u WHERE u.username LIKE '"+username+"%' ORDER BY u.username DESC");
        return q.getResultList();
    }
}
