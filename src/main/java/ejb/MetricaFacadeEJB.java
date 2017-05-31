package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.MetricaFacade;
import model.Metrica;

import java.util.List;

@Stateless
public class MetricaFacadeEJB extends AbstractFacade<Metrica> implements MetricaFacade {	
	
    @PersistenceContext(unitName = "politweetsPU")
    private EntityManager em;

    public MetricaFacadeEJB() {
        super(Metrica.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    @Override
    public Metrica findByName(String name) {
        List<Metrica> metricas = this.findAll();
        for (Metrica metrica : metricas) {
            if(metrica.getNombre().equals(name)){
                return metrica;
            }
        }
        return null;
    }
}
