package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Adres;
import be.vdab.fietsacademy.domain.Campus;
import be.vdab.fietsacademy.domain.TelefoonNr;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaCampusRepository.class)
@Sql("/insertCampus.sql")
@Sql("/insertDocent.sql")
public class JpaCampusRepositoryTest
        extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String CAMPUSSEN = "campussen";

    @Autowired
    private JpaCampusRepository repository;

    private Campus campus;

    @Before
    public void before() {
        campus = new Campus("test", new Adres("test", "test", "test", "test"));
    }

    private long idVanTestCampus() {
        return super.jdbcTemplate.queryForObject(
                "select id from campussen where naam='test'", Long.class);
    }

    @Test
    public void findById() {
        Campus campus = repository.findById(idVanTestCampus()).get();
        assertThat(campus.getNaam()).isEqualTo("test");
        assertThat(campus.getAdres().getGemeente()).isEqualTo("test");
    }

    @Test
    public void findByOnbestaandeId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @Test
    public void create() {
        repository.create(campus);
        assertThat(super.countRowsInTableWhere(CAMPUSSEN,
                "id=" + campus.getId())).isOne();
    }

    @Test
    public void readTelefonNumber() {
        assertThat(repository.findById(idVanTestCampus()).get().getTelefoonNrs())
                .containsOnly(new TelefoonNr("1", false, "test"));
    }

    @Test
    public void docentenLazyLoaded() {
        assertThat(repository.findById(idVanTestCampus()).get().getDocenten())
                .hasSize(2)
                .first().extracting(docent -> docent.getVoornaam()).isEqualTo("testM");
    }
}

