package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user (" +
                    "   id BIGINT NOT NULL AUTO_INCREMENT," +
                    "   name VARCHAR(20)," +
                    "   last_name VARCHAR(20)," +
                    "   age TINYINT UNSIGNED," +
                    "   primary key (id)" +
                    ")").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "INSERT INTO user(name, last_name, age) VALUES(:name, :lastName, :age)";
        try {
            SQLQuery sqlQuery = session.createSQLQuery(sql);
            sqlQuery.setParameter("name", name);
            sqlQuery.setParameter("lastName", lastName);
            sqlQuery.setParameter("age", age);
            sqlQuery.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "DELETE FROM user WHERE id = :id";
        try {
            session.createSQLQuery(sql)
                   .setParameter("id", id)
                   .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        String sql = "SELECT * FROM user";
        List<User> result = null;
        try {
            session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery(sql)
                                       .addEntity(User.class);
            result = sqlQuery.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        String sql = "DELETE FROM user";
        Transaction transaction = session.beginTransaction();
        try {
            session.createSQLQuery(sql)
                   .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
