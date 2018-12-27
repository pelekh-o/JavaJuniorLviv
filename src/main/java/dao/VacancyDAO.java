package dao;

import entity.Vacancy;

import java.util.Date;
import java.util.List;

public interface VacancyDAO {
    void addVacancy(Vacancy vacancy);
    Vacancy getVacancyById(Integer id);
    List<Vacancy> getAllVacancies();
    List<Vacancy> getVacanciesByCompany(String companyName);
    List<Vacancy> getNewVacancies();
    List<Vacancy> getVacanciesByDate(Date date);
    void updateVacancy(Vacancy vacancy);
    void deleteVacancy(Vacancy vacancy);
}
