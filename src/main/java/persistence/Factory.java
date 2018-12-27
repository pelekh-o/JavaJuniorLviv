package persistence;

import dao.VacancyDAO;
import dao.implementation.VacancyDAOImpl;

public class Factory {
    private static VacancyDAO vacancyDAO = null;
    private static Factory instance = null;

    public static synchronized Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    public VacancyDAO getVacancyDAO() {
        if (vacancyDAO == null) {
            vacancyDAO = new VacancyDAOImpl();
        }
        return vacancyDAO;
    }
}
