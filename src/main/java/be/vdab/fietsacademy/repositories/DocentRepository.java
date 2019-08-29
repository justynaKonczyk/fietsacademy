package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Docent;
import be.vdab.fietsacademy.queryresults.AmoutDocentenPerSalary;
import be.vdab.fietsacademy.queryresults.IdEnEmailAdres;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface DocentRepository {

    Optional<Docent> findById(long id);
    void create(Docent docent);
    void delete(long id);
    List<Docent> findAll();
    List<Docent> findBySalaryBetween(BigDecimal van, BigDecimal tot);
    List<String> findEmailAdress();
    List<IdEnEmailAdres> findIdsEnEmailAdress();
    BigDecimal findHighestSalary();
    List<AmoutDocentenPerSalary> findAmoutDocentenPerSalary();
    int algemeneOpslag(BigDecimal percentage);
    Optional<Docent> findByIdWithLock(long id);

}