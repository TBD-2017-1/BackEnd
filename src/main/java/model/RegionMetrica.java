package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;


/**
 * The persistent class for the region database table.
 * 
 */
@Entity
@Table(name="region_metrica")
@NamedQuery(name="RegionMetrica.findAll", query="SELECT rm FROM RegionMetrica rm")
public class RegionMetrica implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
    @JoinColumn(name="idregion", referencedColumnName="id")
    private Region region_metrica;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idmetrica", referencedColumnName="id")
    private Metrica metrica_region;
    
    public RegionMetrica(){
        
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

    public Region getRegion() {
        return region_metrica;
    }

    public void setRegion(Region region_metrica) {
        this.region_metrica = region_metrica;
    }

    public Metrica getMetrica() {
        return metrica_region;
    }

    public void setMetrica(Metrica metrica_region) {
        this.metrica_region = metrica_region;
    }
    
}
