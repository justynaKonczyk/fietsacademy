package be.vdab.fietsacademy.domain;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class DocentTest {

    private static BigDecimal WEDDE = BigDecimal.valueOf(200);

    private Docent docent1;

    @Before
    public void before(){
        docent1 = new Docent("test", "test", WEDDE, "test@fietsacademy.be", Geslacht.MAN);
    }

    @Test
    public void raise(){
        docent1.opslag(BigDecimal.TEN);
        assertThat(docent1.getWedde()).isEqualByComparingTo("220");
    }

    @Test
    public void raiseNullDoesntWork(){
        assertThatNullPointerException().isThrownBy(()->docent1.opslag(null));
    }

    @Test
    public void raiseZeroDoesntWork(){
        assertThatIllegalArgumentException().isThrownBy(()->docent1.opslag(BigDecimal.ZERO));
    }

    @Test
    public void negativeRaiseDoesntWork(){
        assertThatIllegalArgumentException().isThrownBy(()-> docent1.opslag(BigDecimal.valueOf(-1)));
    }
}
