package be.vdab.fietsacademy.queryresults;

import java.math.BigDecimal;

public class AmoutDocentenPerSalary {

    private final BigDecimal wedde;
    private final long aantal;

    public AmoutDocentenPerSalary(BigDecimal wedde, long aantal) {
        this.wedde = wedde;
        this.aantal = aantal;
    }

    public BigDecimal getWedde() {
        return wedde;
    }

    public long getAantal() {
        return aantal;
    }
}
