package persistence;

import entity.Vacancy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class HibernateUtil {
    private static Logger logger = LogManager.getLogger(HibernateUtil.class.getName());


    private static final SessionFactory sessionFactory;

    static {
        Properties properties = new Properties();
        ClassLoader classLoader = HibernateUtil.class.getClassLoader();

        try {
            properties.load(new FileInputStream(classLoader.getResource("db.properties").getFile()));
        } catch (IOException ex) {
            logger.error("Error while reading db.properties file. Error: {}" + ex.getMessage());
        }

        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            configuration.setProperty("hibernate.connection.username", properties.getProperty("connection.username"))
                    .setProperty("hibernate.connection.password", properties.getProperty("connection.password"));
            configuration.addAnnotatedClass(Vacancy.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (ExceptionInInitializerError ex) {
            logger.error(ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Gets hiberante session factory that was initialized at application startup.
     *
     * @return hibernate session factory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}