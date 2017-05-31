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
@Table(name="conglomerado_metrica")
@NamedQuery(name="ConglomeradoMetrica.findAll", query="SELECT cm FROM ConglomeradoMetrica cm")
public class ConglomeradoMetrica implements Serializable {
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
    @JoinColumn(name="idconglomerado", referencedColumnName="id")
    private Conglomerado conglomerado_metrica;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idmetrica", referencedColumnName="id")
    private Metrica metrica_conglomerado;

    //Methods
    public ConglomeradoMetrica() {
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

    public Conglomerado getConglomerado() {
        return conglomerado_metrica;
    }

    public void setConglomerado(Conglomerado conglomerado_metrica) {
        this.conglomerado_metrica = conglomerado_metrica;
    }

    public Metrica getMetrica() {
        return metrica_conglomerado;
    }

    public void setMetrica(Metrica metrica_conglomerado) {
        this.metrica_conglomerado = metrica_conglomerado;
    }   
    
}