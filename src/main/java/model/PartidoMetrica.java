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
@Table(name="partido_metrica")
@NamedQuery(name="PartidoMetrica.findAll", query="SELECT pm FROM PartidoMetrica pm")
public class PartidoMetrica implements Serializable {
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
    @JoinColumn(name="idpartido", referencedColumnName="id")
    private Partido partido_metrica;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idmetrica", referencedColumnName="id")
    private Metrica metrica_partido;

    //Methods
    public PartidoMetrica() {
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

    public Partido getPartido_metrica() {
        return this.partido_metrica;
    }
    
    public void setPartido_metrica(Partido partido_metrica){
        this.partido_metrica = partido_metrica;
    }

    public Metrica getMetrica_partido() {
        return metrica_partido;
    }

    public void setMetrica_partido(Metrica metrica_partido) {
        this.metrica_partido = metrica_partido;
    }
    
    
}