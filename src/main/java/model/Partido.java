package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;


/**
 * The persistent class for the partido database table.
 * 
 */
@Entity
@Table(name="partido")
@NamedQuery(name="Partido.findAll", query="SELECT p FROM Partido p")
public class Partido implements Serializable {
    private static final long serialVersionUID = 1L;

    //Atributes
    @Id
    @Column(name="id", nullable=false, unique=true)
    private int id;

    @Column(name="nombre", nullable=false, unique=true, length=128)
    private String nombre;

    @Column(name="cuentaTwitter", length=45)
    private String cuentaTwitter;

    //Relations
    @ManyToOne
    @JoinColumn(name="idconglomerado", referencedColumnName="id")
    private Conglomerado conglomerado_partido;

    @OneToMany(mappedBy="partido_politico")
    private List<Politico> listaPoliticos;

    @OneToMany(mappedBy="partido_metrica")
    private List<PartidoMetrica> partidoMetrica;
    
    @ManyToMany(mappedBy="partidos_keywords", cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    private List<Keyword> keywords;

    //Methods
    public Partido() {
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

    public String getCuentaTwitter() {
        return cuentaTwitter;
    }

    public void setCuentaTwitter(String cuentaTwitter) {
        this.cuentaTwitter = cuentaTwitter;
    }

    public Conglomerado getConglomerado() {
        return conglomerado_partido;
    }

    public void setConglomerado(Conglomerado conglomerado){
        this.conglomerado_partido = conglomerado;
    }

    public List<Politico> getListaPoliticos() {
        return listaPoliticos;
    }

    public void addPolitico(Politico politico) {
        this.listaPoliticos.add(politico);
    }

    public List<PartidoMetrica> getPartidoMetrica() {
        return partidoMetrica;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void addKeyword(Keyword keyword) {
        this.keywords.add(keyword);
    }
    
    public void removeKeyword(Keyword keyword){
        List<Keyword> toRemove = new ArrayList<>();
        for (Keyword pk : this.keywords) {
            if(pk.getId() == keyword.getId()){
                toRemove.add(pk);
            }
        }
        this.keywords.removeAll(toRemove);
    }
}