package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the metrica database table.
 * 
 */
@Entity
@Table(name="metrica")
@NamedQuery(name="Metrica.findAll", query="SELECT m FROM Metrica m")
public class Metrica implements Serializable {
    private static final long serialVersionUID = 1L;
    
    //Atributes
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    private int id;
    
    @Column(name="nombre", nullable=false, length=45)
    private String nombre;
    
    //Relations
    @OneToMany(mappedBy="metrica_conglomerado")
    @OrderBy("fecha DESC")
    private List<ConglomeradoMetrica> conglomeradoMetrica;
    
    @OneToMany(mappedBy="metrica_partido")
    @OrderBy("fecha DESC")
    private List<PartidoMetrica> partidoMetrica;
    
    @OneToMany(mappedBy="metrica_politico")
    @OrderBy("fecha DESC")
    private List<PoliticoMetrica> politicoMetrica;

    @OneToMany(mappedBy="metrica_region")
    @OrderBy("fecha DESC")
    private List<RegionMetrica> regionMetrica;
    
    //Methods
    public Metrica() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ConglomeradoMetrica> getConglomeradoMetrica() {
        return conglomeradoMetrica;
    }

    public List<PartidoMetrica> getPartidoMetrica() {
        return partidoMetrica;
    }

    public List<PoliticoMetrica> getPoliticoMetrica() {
        return politicoMetrica;
    }

    public List<RegionMetrica> getRegionMetrica() {
        return regionMetrica;
    }
    
}