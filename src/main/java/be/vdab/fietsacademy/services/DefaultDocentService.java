package be.vdab.fietsacademy.services;

import be.vdab.fietsacademy.domain.Docent;
import be.vdab.fietsacademy.exceptions.DocentNotFound;
import be.vdab.fietsacademy.repositories.DocentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
public class DefaultDocentService implements DocentService {

    private final DocentRepository docentRepository;

    public DefaultDocentService(DocentRepository docentRepository) {
        this.docentRepository = docentRepository;
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void opslag(long id, BigDecimal percentage) {

        Optional<Docent> optionalDocent = docentRepository.findById(id);

        if (optionalDocent.isPresent()){
            optionalDocent.get().opslag(percentage);
        }else {
            throw new DocentNotFound();
        }
    }
}
