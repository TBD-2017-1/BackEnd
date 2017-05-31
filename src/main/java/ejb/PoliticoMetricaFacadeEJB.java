package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.PoliticoMetricaFacade;
import model.PoliticoMetrica;

@Stateless
public class PoliticoMetricaFacadeEJB extends AbstractFacade<PoliticoMetrica> implements PoliticoMetricaFacade {	
	
    @PersistenceContext(unitName = "politweetsPU")
    private EntityManager em;

    public PoliticoMetricaFacadeEJB() {
        super(PoliticoMetrica.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

}
