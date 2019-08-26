package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Cursus;

import java.util.Optional;

public interface CursusRepository {

//    Optional<Cursus> findById(long id);

    Optional<Cursus> findById(String id);
    void create(Cursus cursus);

}
