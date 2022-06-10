package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.transaction.Transactional;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String SQL_ADD_USER = "INSERT INTO user (name,lastName,age) values(?,?,?)";
    private static final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS USER";
    private static final String CLEAN_USERS_TABLE = "TRUNCATE TABLE USER";
    private static final String REMOVE_USER_BY_ID = "DELETE from user WHERE id=?";
    private static final String FIND_ALL_USERS = "SELECT * from user";
    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS USER " +
            "(id INTEGER not NULL AUTO_INCREMENT, " +
            " name VARCHAR(255), " +
            " lastName VARCHAR(255), " +
            " age INTEGER, " +
            " PRIMARY KEY ( id ))";

    public UserDaoHibernateImpl() {

    }

    @Transactional
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(CREATE_USERS_TABLE).executeUpdate();
            transaction.commit();
            System.out.println("Created table Users in given database...");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Transactional
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(DROP_USERS_TABLE).executeUpdate();
            transaction.commit();
            System.out.println("Table Users in given database is dropped");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(createUser(name, lastName, age));
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("User was not added");
            }
            e.printStackTrace();
        }
    }


    public void removeUserById(long id) {
        User user;
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            user = session.load(User.class, id);
            session.delete(user);
            session.flush();
            transaction.commit();
            System.out.println("User deleted");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("User was not deleted");
            }
            e.printStackTrace();
        }
    }


    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }


    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(CLEAN_USERS_TABLE).executeUpdate();
            transaction.commit();
            System.out.println("Table Users is cleaned");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private User createUser(String name, String lastName, byte age) {
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        return user;
    }
}
