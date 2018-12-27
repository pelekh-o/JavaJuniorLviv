package dao.implementation;

import dao.VacancyDAO;
import entity.Vacancy;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistence.HibernateUtil;

import java.util.Date;
import java.util.List;

public class VacancyDAOImpl implements VacancyDAO {
    @Override
    public void addVacancy(Vacancy vacancy) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(vacancy);
        transaction.commit();
        session.close();
    }

    @Override
    public Vacancy getVacancyById(Integer id) {
        return HibernateUtil.getSessionFactory().openSession().get(Vacancy.class, id);
    }

    @Override
    public List getVacanciesByCompany(String companyName) {
        return HibernateUtil.getSessionFactory().openSession()
                .createQuery("select v from Vacancy v where v.companyName = :companyName")
                .setParameter("companyName", companyName)
                .list();
    }

    @Override
    public List getNewVacancies() {
        return HibernateUtil.getSessionFactory().openSession()
                .createQuery("select v from Vacancy v where v.isNew = true")
                .list();
    }

    @Override
    public List getAllVacancies() {
        return HibernateUtil.getSessionFactory().openSession().createQuery("select v from Vacancy v").list();
    }

    @Override
    public List getVacanciesByDate(Date date) {
        return HibernateUtil.getSessionFactory().openSession()
                .createQuery("select v from Vacancy v where v.updated = :date")
                .setParameter("date", date)
                .list();
    }

    @Override
    public void updateVacancy(Vacancy vacancy) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(vacancy);
        transaction.commit();
        session.close();

    }

    @Override
    public void deleteVacancy(Vacancy vacancy) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(vacancy);
        transaction.commit();
        session.close();
    }
}
