package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Dima", "Petrenko", (byte)28);
        userService.saveUser("Kirill", "Simonov", (byte)25);
        userService.saveUser("Marusya", "Nikiforova", (byte)19);
        userService.saveUser("Masha", "Petrova", (byte)40);

        for (User user: userService.getAllUsers()) {
            System.out.println(user);
        }

//        userService.removeUserById(101);

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
