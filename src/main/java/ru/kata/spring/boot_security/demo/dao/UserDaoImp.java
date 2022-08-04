package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Repository
public class UserDaoImp implements UserDao {

   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public void add(User user) {
      entityManager.persist(user);
   }

   @Override
   public User getUser(Long id) {
      return entityManager.find(User.class, id);
   }

   @Override
   public User getUserByUsername(String username) {
      return (User) entityManager.createQuery("select u from User u left join fetch u.roles where u.username = :username").setParameter("username", username).getResultList().get(0);
   }

   @Override
   public Set<User> listUsers() {
      return new HashSet<User>(entityManager.createQuery("select u from User u left join fetch u.roles").getResultList());
   }

   @Override
   public void update(User user) {
      entityManager.merge(user);
   }

   @Override
   public void delete(Long id) {
      entityManager.remove(getUser(id));
   }
}
