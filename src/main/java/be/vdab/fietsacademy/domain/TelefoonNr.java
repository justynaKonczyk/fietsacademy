package be.vdab.fietsacademy.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TelefoonNr implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nummer;
    private boolean fax;
    private String opmerking;

    public TelefoonNr(String nummer, boolean fax, String opmerking) {
        this.nummer = nummer;
        this.fax = fax;
        this.opmerking = opmerking;
    }

    protected TelefoonNr() {
    }

    public String getNummer() {
        return nummer;
    }

    public boolean isFax() {
        return fax;
    }

    public String getOpmerking() {
        return opmerking;
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof TelefoonNr)){
            return false;
        }
        TelefoonNr telefoonNr = (TelefoonNr) o;
        return nummer.equalsIgnoreCase(telefoonNr.nummer);
    }

    @Override
    public int hashCode(){
        return  nummer.toUpperCase().hashCode();
    }
}
