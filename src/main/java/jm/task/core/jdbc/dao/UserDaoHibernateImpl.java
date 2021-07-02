package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.SessionException;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSession()) {
            session.beginTransaction();
            String sql = "CREATE TABLE if not exists USER(\n" +
                    "    id       int         NOT NULL PRIMARY KEY AUTO_INCREMENT,\n" +
                    "    name     VARCHAR(50) NOT NULL,\n" +
                    "    lastName VARCHAR(50) NOT NULL,\n" +
                    "    age      smallint\n" +
                    ")";

            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
        } catch (QueryException e) {
            System.err.println("Ошибка при создании таблицы");
            e.printStackTrace();
        } catch (HibernateException e) {
            System.err.println("Ошибка при попытке соединиться с БД");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSession()) {
            session.beginTransaction();
            String sql = "drop table if exists user";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
        } catch (QueryException e) {
            System.err.println("Ошибка при удалении таблицы из БД");
            e.printStackTrace();
        } catch (HibernateException e) {
            System.err.println("Ошибка при попытке соединиться с БД");
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
        } catch (SessionException e) {
            System.err.println("Ошибка при попытке сохранить User в БД");
            e.printStackTrace();
        } catch (HibernateException e) {
            System.err.println("Ошибка при попытке соединиться с БД");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSession()) {
            session.beginTransaction();
            User user = session.load(User.class, id);
            session.remove(user);
            session.flush();
        } catch (SessionException e) {
            System.err.println("Ошибка при попытке удалить User по ID из БД");
            e.printStackTrace();
        } catch (HibernateException e) {
            System.err.println("Ошибка при попытке соединиться с БД");
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = Util.getSession()) {
            users = (List<User>) session.createQuery("From User").list();
        } catch (QueryException e) {
            System.err.println("Ошибка при попытке получить всех User из БД");
            e.printStackTrace();
        } catch (HibernateException e) {
            System.err.println("Ошибка при попытке соединиться с БД");
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSession()) {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        } catch (QueryException e) {
            System.err.println("Ошибка при попытке удалить всех User из БД");
            e.printStackTrace();
        } catch (HibernateException e) {
            System.err.println("Ошибка при попытке соединиться с БД");
            e.printStackTrace();
        }
    }
}
