package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;


/**
 * The persistent class for the conglomerado database table.
 * 
 */
@Entity
@Table(name="politico_metrica")
@NamedQuery(name="PoliticoMetrica.findAll", query="SELECT pm FROM PoliticoMetrica pm")
public class PoliticoMetrica implements Serializable {
    private static final long serialVersionUID = 1L;

    //Atributes
    @Id
    @Column(name="id", nullable=false, unique=true)
    private int id;

    @Column(name="valor")
    private float valor;

    @Temporal(TemporalType.DATE)
    @Column(name="fecha")
    private Date fecha;
    
    @Column(name="lugar", length=50)
    private String lugar;

    //Relations
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idpolitico", referencedColumnName="id")
    private Politico politico_metrica;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idmetrica", referencedColumnName="id")
    private Metrica metrica_politico;
    
    //Methods
    public PoliticoMetrica() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Politico getPolitico_metrica() {
        return politico_metrica;
    }

    public void setPolitico_metrica(Politico politico_metrica) {
        this.politico_metrica = politico_metrica;
    }

    public Metrica getMetrica_politico() {
        return metrica_politico;
    }

    public void setMetrica_politico(Metrica metrica_politico) {
        this.metrica_politico = metrica_politico;
    }
    
    

}