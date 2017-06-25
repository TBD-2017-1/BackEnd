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
@NamedQuery(name="Region.findAll", query="SELECT c FROM Region c")
public class Region implements Serializable {
    private static final long serialVersionUID = 1L;

    //Atributes
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private int id;

    @Column(name="codigo", nullable=false, unique=true, length=5)
    private String codigo;

    @Column(name="nombre", nullable=false, unique=true, length=45)
    private String nombre;

    //Relations
    @OneToMany(mappedBy="region")
    @OrderBy("fecha DESC")
    private List<ConglomeradoMetrica> conglomeradoMetrica;

    @OneToMany(mappedBy="region")
    @OrderBy("fecha DESC")
    private List<PartidoMetrica> partidoMetrica;

    @OneToMany(mappedBy="region")
    @OrderBy("fecha DESC")
    private List<PoliticoMetrica> politicoMetrica;

    @ManyToMany(mappedBy="region_keywords", cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    private List<Keyword> keywords;

    //Methods
    public Region() {
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<ConglomeradoMetrica> getConglomeradoMetrica() {
        return conglomeradoMetrica;
    }

    public List<ConglomeradoMetrica> getConglomeradoMetrica(String nombreMetrica){
        List<ConglomeradoMetrica> metricas = new ArrayList<>();
        for (ConglomeradoMetrica cm : conglomeradoMetrica) {
            if(cm.getMetrica().getNombre().equals(nombreMetrica)){
                metricas.add(cm);
            }
        }
        return metricas;
    }

    public List<PartidoMetrica> getPartidoMetrica() {
        return partidoMetrica;
    }

    public List<PartidoMetrica> getPartidoMetrica(String nombreMetrica){
        List<PartidoMetrica> metricas = new ArrayList<>();
        for (PartidoMetrica cm : partidoMetrica) {
            if(cm.getMetrica().getNombre().equals(nombreMetrica)){
                metricas.add(cm);
            }
        }
        return metricas;
    }

    public List<PoliticoMetrica> getPoliticoMetrica() {
        return politicoMetrica;
    }

    public List<PoliticoMetrica> getPoliticoMetrica(String nombreMetrica){
        List<PoliticoMetrica> metricas = new ArrayList<>();
        for (PoliticoMetrica cm : politicoMetrica) {
            if(cm.getMetrica().getNombre().equals(nombreMetrica)){
                metricas.add(cm);
            }
        }
        return metricas;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void addKeyword(Keyword keyword){
        this.keywords.add(keyword);
    }

    public void removeKeyword(Keyword keyword){
        List<Keyword> toRemove = new ArrayList<>();
        for (Keyword ck : this.keywords) {
            if(ck.getId() == keyword.getId()){
                toRemove.add(ck);
            }
        }
        this.keywords.removeAll(toRemove);
    }
}