package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

public class Main {
    public static void main(String[] args) {
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();

        userDaoHibernate.createUsersTable();

        userDaoHibernate.saveUser("Ivan", "Ivanov", (byte) 20);
        System.out.println("User с именем – Ivan добавлен в базу данных");

        userDaoHibernate.saveUser("Kirill", "Kirillov", (byte) 18);
        System.out.println("User с именем – Kirill добавлен в базу данных");

        userDaoHibernate.saveUser("Aleksandr", "Aleksandrov", (byte) 35);
        System.out.println("User с именем – Aleksandr добавлен в базу данных");

        userDaoHibernate.saveUser("Igor", "Igorev", (byte) 15);
        System.out.println("User с именем – Igor добавлен в базу данных");

        userDaoHibernate.removeUserById(1);
        System.out.println("Проверка должно быть 3 User");

        userDaoHibernate.getAllUsers().forEach(System.out::println);

        userDaoHibernate.cleanUsersTable();

        userDaoHibernate.dropUsersTable();


    }
}
