package be.vdab.fietsacademy.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "campussen")
public class Campus implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;

    @Embedded
    private Adres adres;

    public Campus(String naam, Adres adres) {
        this.naam = naam;
        this.adres = adres;
    }

    protected Campus() {
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public Adres getAdres() {
        return adres;
    }
}
