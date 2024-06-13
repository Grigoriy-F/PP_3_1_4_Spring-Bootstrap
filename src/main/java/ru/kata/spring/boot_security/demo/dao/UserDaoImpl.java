package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.*;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    //    @Override
//    public User findByEmail(String email) {
//        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = : e");
//        query.setParameter("e", email);
//        return (User) query.getSingleResult();
//    }
    @Override
    public User findByEmail(String email) {
        try {
            Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email");
            query.setParameter("email", email);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("User with email '" + email + "' not found");
        }
    }

    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("SELECT u FROM User u").getResultList();
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    //    @Override
//    public User findUser(int id) {
//        return entityManager.find(User.class, id);
//    }
    @Override
    public User findUser(int id) {
        try {
            return entityManager.find(User.class, id);
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException("User with id '" + id + "' not found");
        }
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    //    @Override
//    public void delete(int id) {
//        User user = entityManager.find(User.class, id);
//        System.out.println(user);
//        entityManager.remove(user);
//    }
    @Override
    public void delete(int id) {
        try {
            User user = entityManager.getReference(User.class, id);
            entityManager.remove(user);
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException("User with id '" + id + "' not found");
        }
    }
}
