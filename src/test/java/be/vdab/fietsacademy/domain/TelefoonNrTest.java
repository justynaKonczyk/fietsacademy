package be.vdab.fietsacademy.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TelefoonNrTest {

    private TelefoonNr nummer1, nogEensNUmmer1, nummer2;

    @Before
    public void before(){
        nummer1 = new TelefoonNr("1", false, "");
        nogEensNUmmer1 = new TelefoonNr("1", false, "");
        nummer2 = new TelefoonNr("2", false, "");
    }

    @Test
    public void telefonNrAreTheSame(){
        assertThat(nummer1).isEqualTo(nogEensNUmmer1);
    }

    @Test
    public void telefonNrAreDifferent(){
        assertThat(nummer1).isNotEqualTo(nummer2);
    }

    @Test
    public void telefonNrIsNotNull(){
        assertThat(nummer1).isNotEqualTo(null);
    }

    @Test
    public void telefonNrIsDifferentThanOtherTypeObject(){
        assertThat(nummer1).isNotEqualTo("");
    }

    @Test
    public void sameNumbersGivesSameHashCode(){
        assertThat(nummer1).hasSameHashCodeAs(nogEensNUmmer1);
    }
}
