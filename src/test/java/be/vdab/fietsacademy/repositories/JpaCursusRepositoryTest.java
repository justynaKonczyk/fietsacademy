package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Cursus;
import be.vdab.fietsacademy.domain.GroepsCursus;
import be.vdab.fietsacademy.domain.IndividueleCursus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaCursusRepository.class)
@Sql("/insertCursus.sql")
public class JpaCursusRepositoryTest
    extends AbstractTransactionalJUnit4SpringContextTests {

//    private static final String CURSUSSEN = "cursussen";
    private static final LocalDate EEN_DATUM = LocalDate.of(2019,1,1);

    private static final String GROEPS_CURSUSSEN = "groepscursussen";
    private static final String INDIVIDUELE_CURSUSSEN = "individuelecursussen";

    @Autowired
    private JpaCursusRepository cursusRepository;

    @Autowired
    private EntityManager manager;

    private String idOfTestGroupCourse(){
        return super.jdbcTemplate.queryForObject(
                "select id from groepscursussen where naam = 'testGroep'", String.class);
    }

    private String idOfTestIndividualCourse(){
        return super.jdbcTemplate.queryForObject(
                "select id from individuelecursussen where naam = 'testIndividueel'", String.class);
    }

    @Test
    public void findGroupCourseById(){
        Optional<Cursus> optionalCursus =
                cursusRepository.findById(idOfTestGroupCourse());
        assertThat(((GroepsCursus)  optionalCursus.get()).getNaam())
                .isEqualTo("testGroep");
    }

    @Test
    public void findIndividualCourseById(){
        Optional<Cursus> optionalCursus =
                cursusRepository.findById(idOfTestIndividualCourse());
        assertThat(((IndividueleCursus) optionalCursus.get()).getNaam())
                .isEqualTo("testIndividueel");
    }

    @Test
    public void findByNonExistingId(){
        assertThat(cursusRepository.findById("")).isNotPresent();
    }

    @Test
    public void createGroupCourse(){
        GroepsCursus groepsCursus = new GroepsCursus("testGroup2", EEN_DATUM, EEN_DATUM);
        cursusRepository.create(groepsCursus);
        manager.flush();
//        assertThat(super.countRowsInTableWhere(CURSUSSEN, "id= ' " + groepsCursus.getId() + " ' "))
//                .isOne();
        assertThat(super.countRowsInTableWhere(GROEPS_CURSUSSEN, "id= '" + groepsCursus.getId() + "'"))
                .isOne();
    }

    @Test
    public void createIndividualCourse(){
        IndividueleCursus individueleCursus = new IndividueleCursus("testIndividueel12", 7);
        cursusRepository.create(individueleCursus);
        manager.flush();
//        assertThat(super.countRowsInTableWhere(CURSUSSEN, "id= '" + individueleCursus.getId()+"'"))
//                .isOne();
        assertThat(super.countRowsInTableWhere(INDIVIDUELE_CURSUSSEN, "id='" + individueleCursus.getId() + "'"))
                .isOne();
    }
}
