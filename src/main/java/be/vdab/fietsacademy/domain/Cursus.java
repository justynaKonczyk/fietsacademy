package be.vdab.fietsacademy.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Inheritance(strategy = InheritanceType.JOINED)
//@Table(name = "cursussen")
public abstract class Cursus implements Serializable {
        private static final long serialVersionUID = 1L;


        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private String id;
        private String naam;

    public Cursus(String naam) {
        id = UUID.randomUUID().toString();
        this.naam = naam;
    }

    protected Cursus() {
    }

    public String getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }
}
