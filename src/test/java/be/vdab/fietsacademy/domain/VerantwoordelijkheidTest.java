package be.vdab.fietsacademy.domain;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class VerantwoordelijkheidTest {

    private Verantwoordelijkheid verantwoordelijkheid1;
    private Docent docent1;
    private Campus campus1;

    @Before
    public void before(){
        verantwoordelijkheid1 = new Verantwoordelijkheid("EHBO");
        campus1 = new Campus("test", new Adres("test", "test", "test", "test"));
        docent1 = new Docent("test", "test", BigDecimal.TEN, "test#fietsacademy.be", Geslacht.MAN, campus1);
    }

    @Test
    public void addDocent(){
        assertThat(verantwoordelijkheid1.getDocenten()).isEmpty();
        assertThat(verantwoordelijkheid1.add(docent1)).isTrue();
        assertThat(verantwoordelijkheid1.getDocenten()).hasSize(1);
        assertThat(verantwoordelijkheid1.getDocenten()).contains(docent1);
        assertThat(docent1.getVerantwoordelijkheden()).hasSize(1);
        assertThat(docent1.getVerantwoordelijkheden()).contains(verantwoordelijkheid1);
    }

    @Test
    public void removeDocent(){
        assertThat(verantwoordelijkheid1.add(docent1)).isTrue();
        assertThat(verantwoordelijkheid1.remove(docent1)).isTrue();
        assertThat(verantwoordelijkheid1.getDocenten()).isEmpty();
        assertThat(docent1.getVerantwoordelijkheden()).isEmpty();
    }
}
