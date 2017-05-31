package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.ConglomeradoMetricaFacade;
import java.util.List;
import model.ConglomeradoMetrica;

@Stateless
public class ConglomeradoMetricaFacadeEJB extends AbstractFacade<ConglomeradoMetrica> implements ConglomeradoMetricaFacade {
	
    @PersistenceContext(unitName = "politweetsPU")
    private EntityManager em;

    public ConglomeradoMetricaFacadeEJB() {
        super(ConglomeradoMetrica.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
}
