package be.vdab.fietsacademy.domain;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "docenten")
@NamedQuery(name = "Docent.findBySalaryBetween",
        query = "select d from Docent d where d.wedde between :van and :tot" +
                " order by d.wedde, d.id")
@NamedEntityGraph(name = "Docent.MET_CAMPUS",
        attributeNodes = @NamedAttributeNode("campus"))

public class Docent implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String MET_CAMPUS = "Docent.metCampus";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @SequenceGenerator(name = "myGenerator", sequenceName = "klantleverancierid")
    private long id;

    private String voornaam;
    private String familienaam;

    @NumberFormat
    private BigDecimal wedde;
    private String emailAdres;

    @Enumerated(EnumType.STRING)
    private Geslacht geslacht;

    @ElementCollection
    @CollectionTable(name = "docentenbijnamen", joinColumns = @JoinColumn(name = "docentid"))
    @Column(name = "bijnaam")
    private Set<String> bijnamen;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campusid")
    private Campus campus;

    @ManyToMany(mappedBy = "docenten")
//    @JoinTable(
//            name = "docentenverantwoordelijkheden",
//            joinColumns = @JoinColumn(name="docentId"),
//            inverseJoinColumns = @JoinColumn(name="verantwoordelijkheidId"))
    private Set<Verantwoordelijkheid> verantwoordelijkheiden = new LinkedHashSet<>();

    @Version
    private Timestamp versie;

    protected Docent() {
    }

    public Docent(String voornaam, String familienaam,
                  BigDecimal wedde, String emailAdres, Geslacht geslacht, Campus campus) { //
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.wedde = wedde;
        this.emailAdres = emailAdres;
        this.geslacht = geslacht;
        this.bijnamen = new LinkedHashSet<>();
        setCampus(campus);
    }

    public boolean add(Verantwoordelijkheid verantwoordelijkheid) {
        boolean toegevoegd = verantwoordelijkheiden.add(verantwoordelijkheid);
        if (!verantwoordelijkheid.getDocenten().contains(this)) {
            verantwoordelijkheid.add(this);
        }
        return toegevoegd;
    }

    public boolean remove(Verantwoordelijkheid verantwoordelijkheid) {
        boolean verwijderd = verantwoordelijkheiden.remove(verantwoordelijkheid);
        if (verantwoordelijkheid.getDocenten().contains(this)) {
            verantwoordelijkheid.remove(this);
        }
        return verwijderd;
    }

    public Set<Verantwoordelijkheid> getVerantwoordelijkheden() {
        return Collections.unmodifiableSet(verantwoordelijkheiden);
    }

    public void opslag(BigDecimal percentege) {
        if (percentege.compareTo(BigDecimal.ONE) <= 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal factor = BigDecimal.ONE.add(percentege.divide(BigDecimal.valueOf(100)));
        wedde = wedde.multiply(factor, new MathContext(2, RoundingMode.HALF_UP));
    }

    public Set<String> getBijnamen() {
        return Collections.unmodifiableSet(bijnamen);
    }

    public boolean addBijnaam(String bijnaam) {
        if (bijnaam.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
        return bijnamen.add(bijnaam);
    }

    public boolean removeBijnaam(String bijnaam) {
        return bijnamen.remove(bijnaam);
    }

    public long getId() {
        return id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    public BigDecimal getWedde() {
        return wedde;
    }

    public String getEmailAdres() {
        return emailAdres;
    }

    public Geslacht getGeslacht() {
        return geslacht;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        if (!campus.getDocenten().contains(this)) {
            campus.add(this);
        }
        this.campus = campus;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Docent)) {
            return false;
        }
        if (emailAdres == null) {
            return false;
        }
        return emailAdres.equalsIgnoreCase(((Docent) o).emailAdres);
//        Docent docent = (Docent) o;
//        return this.id==docent.id;
    }

    @Override
    public int hashCode() {
        return emailAdres == null ? 0 : emailAdres.toLowerCase().hashCode();
//        return (int) id;
    }


}
