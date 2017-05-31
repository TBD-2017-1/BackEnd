package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;


/**
 * The persistent class for the conglomerado database table.
 * 
 */
@Entity
@Table(name="conglomerado")
@NamedQuery(name="Conglomerado.findAll", query="SELECT c FROM Conglomerado c")
public class Conglomerado implements Serializable {
    private static final long serialVersionUID = 1L;

    //Atributes
    @Id
    @Column(name="id", nullable=false, unique=true)
    private int id;

    @Column(name="nombre", nullable=false, unique=true, length=45)
    private String nombre;

    @Column(name="cuentaTwitter", unique=true, length=45)
    private String cuentaTwitter;

    //Relations
    @OneToMany(mappedBy="conglomerado_partido")
    private List<Partido> listaPartidos;

    @OneToMany(mappedBy="conglomerado_politico")
    private List<Politico> listaPoliticos;
    
    @OneToMany(mappedBy="conglomerado_metrica")
    private List<ConglomeradoMetrica> conglomeradoMetrica;

    @ManyToMany(mappedBy="conglomerados_keywords", cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    private List<Keyword> keywords;

    //Methods
    public Conglomerado() {
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

    public List<Partido> getListaPartidos() {
        return listaPartidos;
    }

    public void addPartido(Partido partido){
        this.listaPartidos.add(partido);
    }

    public List<Politico> getListaPoliticos() {
        return listaPoliticos;
    }

    public void addPolitico(Politico politico){
        this.listaPoliticos.add(politico);
    }

    public List<ConglomeradoMetrica> getConglomeradoMetrica() {
        return conglomeradoMetrica;
    }
    
    public List<ConglomeradoMetrica> getMetrica(String nombreMetrica){
        List<ConglomeradoMetrica> metricas = new ArrayList<>();
        for (ConglomeradoMetrica cm : conglomeradoMetrica) {
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