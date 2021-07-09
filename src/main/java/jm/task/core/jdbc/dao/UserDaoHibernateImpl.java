package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSession;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = null;

        try {

            session = getSession();
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE if not exists users(\n" +
                    "id       bigint      NOT NULL PRIMARY KEY AUTO_INCREMENT,\n" +
                    "name     VARCHAR(50) NOT NULL,\n" +
                    "lastName VARCHAR(50) NOT NULL,\n" +
                    "age      TINYINT \n" +
                    ")").executeUpdate();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            System.err.println("Ошибка при создании таблицы");
            e.printStackTrace();
            try {
                if (session != null) {
                    session.getTransaction().rollback();
                }
            } catch (HibernateException hibernateException) {
                hibernateException.printStackTrace();
                System.err.println("Попытка отменить изменения, после попытки создания таблицы - неудачна");
            }
            System.err.println("Ошибка при попытке соединиться с БД");
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Ошибка закрытия соединения с БД во время создания таблицы");
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;

        try {
            session = getSession();
            session.beginTransaction();
            session.createSQLQuery("drop table if exists users").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Ошибка при попытке соединиться с БД");
            e.printStackTrace();
            try {
                if (session != null) {
                    session.getTransaction().rollback();
                }
            } catch (HibernateException hibernateException) {
                hibernateException.printStackTrace();
                System.err.println("Попытка отменить изменения, после попытки создания таблицы - неудачна");
            }
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Ошибка закрытия соединения с БД при удалении таблицы из БД");
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;

        try {
            session = getSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Ошибка при сохранении User");
            e.printStackTrace();
            try {
                if (session != null) {
                    session.getTransaction().rollback();
                }
            } catch (HibernateException hibernateException) {
                hibernateException.printStackTrace();
                System.err.println("Попытка отменить изменения, после попытки сохранить User - неудачна");
            }
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Ошибка закрытия соединения с БД при попытке сохранить User в БД");
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;

        try {
            session = getSession();
            session.beginTransaction();
            session.createQuery("delete " + User.class.getName() + " where id = :id")
                    .setParameter("id", id).executeUpdate();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            System.err.println("Ошибка при сохранении User");
            e.printStackTrace();
            try {
                if (session != null) {
                    session.getTransaction().rollback();
                }
            } catch (HibernateException hibernateException) {
                hibernateException.printStackTrace();
                System.err.println("Попытка отменить изменения, после попытки сохранить User - неудачна");
            }

        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Ошибка закрытия соединения с БД при попытке сохранить User в БД");
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = null;

        try (Session session = getSession()) {
            users = (List<User>) session.createQuery("From User").list();
        } catch (HibernateException e) {
            System.err.println("Ошибка при попытке соединиться с БД");
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        try {
            session = getSession();
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Ошибка при очистке таблицы User");
            e.printStackTrace();
            try {
                if (session != null) {
                    session.getTransaction().rollback();
                }
            } catch (HibernateException hibernateException) {
                hibernateException.printStackTrace();
                System.err.println("Попытка отменить изменения, при очистке таблицы User - неудачна");
            }
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e) {
                    System.err.println("Ошибка закрытия соединения с БД при очистке таблицы User в БД");
                    e.printStackTrace();
                }
            }
        }
    }
}
