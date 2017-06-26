package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;


/**
 * The persistent class for the region database table.
 * 
 */
@Entity
@Table(name="region")
@NamedQuery(name="Region.findAll", query="SELECT r FROM Region r")
public class Region implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private int id;
    
    @Column(name="codigo", nullable=false, unique=true, length=5)
    private String codigo;
    
    @Column(name="nombre", nullable=false, unique=true, length=45)
    private String nombre;
    
    @OneToMany(mappedBy="region_metrica")
    private List<RegionMetrica> regionMetrica;
    
    @ManyToMany(mappedBy="regiones_keywords", cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    private List<Keyword> keywords;
    
    public Region(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<RegionMetrica> getRegionMetrica() {
        return regionMetrica;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }
    
    public void addKeyword(Keyword keyword){
        this.keywords.add(keyword);
    }
	
    public void removeKeyword(Keyword keyword){
        List<Keyword> toRemove = new ArrayList<>();
        for (Keyword rk : this.keywords) {
            if(rk.getId() == keyword.getId()){
                toRemove.add(rk);
            }
        }
        this.keywords.removeAll(toRemove);
    }
}
