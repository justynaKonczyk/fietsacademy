package be.vdab.fietsacademy.domain;


import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;


import java.math.BigDecimal;

public class CampusTest {
    private Docent docent1;
    private Campus campus1;
    private Campus campus2;

    @Before
    public void before() {
        campus1 = new Campus("test", new Adres("test", "test", "test", "test"));
        campus2 = new Campus("test2", new Adres("test2","test2","test2","test2"));
        docent1 = new Docent("test", "test", BigDecimal.TEN, "test@fietsacademy.be",
                Geslacht.MAN, campus1);
    }

    @Test
    public void campus1IsDeCampusVanDocent1() {
        assertThat(docent1.getCampus()).isEqualTo(campus1);
        assertThat(campus1.getDocenten()).containsOnly(docent1);
    }

    @Test
    public void docent1VerhuistNaarCampus2() {
        assertThat(campus2.add(docent1)).isTrue();
        assertThat(campus1.getDocenten()).isEmpty();
        assertThat(campus2.getDocenten()).containsOnly(docent1);
        assertThat(docent1.getCampus()).isEqualTo(campus2);
    }

    @Test
    public void eenNullDocentToevoegenMislukt() {
        assertThatNullPointerException().isThrownBy(() -> campus1.add(null));
    }
}