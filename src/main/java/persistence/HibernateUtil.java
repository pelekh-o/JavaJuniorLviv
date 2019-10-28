package persistence;

import entity.Vacancy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static Logger logger = LogManager.getLogger(HibernateUtil.class.getName());


    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            configuration.setProperty("hibernate.connection.username", System.getenv("DBUSER_JAVAVACANCY"))
                    .setProperty("hibernate.connection.password", System.getenv("DBPASS_JAVAVACANCY"));
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