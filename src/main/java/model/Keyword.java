package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the keyword database table.
 * 
 */
@Entity
@Table(name="keyword")
@NamedQuery(name="Keyword.findAll", query="SELECT k FROM Keyword k")
public class Keyword implements Serializable {
    private static final long serialVersionUID = 1L;

    //Atributes
    @Id
    @Column(name="id", nullable=false, unique=true)
    private int id;

    @Column(name="value", nullable=false, unique=true, length=45)
    private String value;

    //Relations
    @ManyToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    @JoinTable
    (
        name="conglomerado_keyword",
        joinColumns={ @JoinColumn(name="idkeyword", referencedColumnName="id") },
        inverseJoinColumns={ @JoinColumn(name="idconglomerado", referencedColumnName="id") }
    )
    private List<Conglomerado> conglomerados_keywords;

    @ManyToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    @JoinTable
    (
        name="partido_keyword",
        joinColumns={ @JoinColumn(name="idkeyword", referencedColumnName="id") },
        inverseJoinColumns={ @JoinColumn(name="idpartido", referencedColumnName="id") }
    )
    private List<Partido> partidos_keywords;

    @ManyToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
    @JoinTable
    (
        name="politico_keyword",
        joinColumns={ @JoinColumn(name="idkeyword", referencedColumnName="id") },
        inverseJoinColumns={ @JoinColumn(name="idpolitico", referencedColumnName="id") }
    )
    private List<Politico> politicos_keywords;

    //Methods
    public Keyword() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Conglomerado> getConglomerados(){
        return this.conglomerados_keywords;
    }

    public void addConglomerado(Conglomerado conglomerado){
        this.conglomerados_keywords.add(conglomerado);
    }
    
    public void removeConglomerado(Conglomerado conglomerado){
        List<Conglomerado> toRemove = new ArrayList<>();
        for (Conglomerado ck : this.conglomerados_keywords) {
            if(ck.getId() == conglomerado.getId()){
                toRemove.add(ck);
            }
        }
        this.conglomerados_keywords.removeAll(toRemove);
    }

    public List<Partido> getPartidos(){
        return this.partidos_keywords;
    }

    public void addPartido(Partido partido){
        this.partidos_keywords.add(partido);
    }
    
    public void removePartido(Partido partido){
        List<Partido> toRemove = new ArrayList<>();
        for (Partido pk : this.partidos_keywords) {
            if(pk.getId() == partido.getId()){
                toRemove.add(pk);
            }
        }
        this.partidos_keywords.removeAll(toRemove);
    }

    public List<Politico> getPoliticos(){
        return this.politicos_keywords;
    }

    public void addPolitico(Politico politicos){
        this.politicos_keywords.add(politicos);
    }
    
    public void removePolitico(Politico politico){
        List<Politico> toRemove = new ArrayList<>();
        for (Politico pk : this.politicos_keywords) {
            if(pk.getId() == politico.getId()){
                toRemove.add(pk);
            }
        }
        this.politicos_keywords.removeAll(toRemove);
    }
    
    public boolean isUnused(){
        if(this.getConglomerados().isEmpty() && this.getPartidos().isEmpty() && this.getPoliticos().isEmpty()){
            return true;
        }
        return false;
    }
}