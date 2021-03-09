package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class Util {
    // реализуйте настройку соеденения с БД
    private static SessionFactory sessionFactory;

    private static SessionFactory configureSessionFactory() {
        Configuration configuration  = new Configuration()
                .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/mydbtest?serverTimezone=UTC")
                .setProperty("hibernate.connection.username", "bestuser")
                .setProperty("hibernate.connection.password", "bestuser")
                .setProperty("hibernate.connection.pool_size", "2")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.show_sql","true")
                .setProperty("hibernate.current_session_context_class", "thread")
                .addAnnotatedClass(User.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null)
            sessionFactory = configureSessionFactory();
        return sessionFactory;
    }
}
