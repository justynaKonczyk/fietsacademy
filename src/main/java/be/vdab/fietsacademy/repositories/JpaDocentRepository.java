package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Docent;
import be.vdab.fietsacademy.queryresults.AmoutDocentenPerSalary;
import be.vdab.fietsacademy.queryresults.IdEnEmailAdres;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
class JpaDocentRepository implements DocentRepository {


    private final EntityManager manager;

    public JpaDocentRepository(EntityManager manager) {
        this.manager = manager;
    }



    @Override
    public void create(Docent docent) {
        manager.persist(docent);

    }

    @Override
    public void delete(long id) {
        findById(id).ifPresent(docent -> manager.remove(docent));
    }

    @Override
    public List<Docent> findAll() {
        return  manager.createQuery("select d from Docent d order by d.wedde", Docent.class)
                .getResultList();
    }

    @Override
    public Optional<Docent> findById(long id) {
        return Optional.ofNullable(manager.find(Docent.class, id));

    }

    @Override
    public Optional<Docent> findByIdWithLock(long id) {
        return Optional.ofNullable(
                manager.find(Docent.class, id, LockModeType.PESSIMISTIC_WRITE));
    }

    @Override
    public List<Docent> findBySalaryBetween(BigDecimal van, BigDecimal tot) {
        return manager.createNamedQuery("Docent.findBySalaryBetween", Docent.class)
        .setParameter("van", van)
        .setParameter("tot", tot)
                .setHint("javax.persistence.loadgraph",
                        manager.createEntityGraph("MET_CAMPUS"))
                .getResultList();

    }

    @Override
    public List<String> findEmailAdress() {
        return manager.createQuery("select d.emailAdres from Docent d", String.class)
                .getResultList();
    }

    @Override
    public List<IdEnEmailAdres> findIdsEnEmailAdress() {
        return manager.createQuery(
                "select new be.vdab.fietsacademy.queryresults.IdEnEmailAdres(d.id, d.emailAdres)" +
                        "from Docent d", IdEnEmailAdres.class).getResultList();
    }

    @Override
    public BigDecimal findHighestSalary() {
        return manager.createQuery(
                "select max (d.wedde) from Docent d", BigDecimal.class)
                .getSingleResult();
    }

    @Override
    public List<AmoutDocentenPerSalary> findAmoutDocentenPerSalary() {
        return manager.createQuery(
                "select new be.vdab.fietsacademy.queryresults.AmoutDocentenPerSalary(" +
                        "d.wedde, count(d)) from Docent d group by d.wedde",
                AmoutDocentenPerSalary.class).getResultList();
    }


    @Override
    public int algemeneOpslag(BigDecimal percentage) {
        BigDecimal factor = BigDecimal.ONE.add(percentage.divide(BigDecimal.valueOf(100)));
        return manager.createNamedQuery("Docent.algemeneOpslag")
                .setParameter("factor", factor)
                .executeUpdate();
    }
}