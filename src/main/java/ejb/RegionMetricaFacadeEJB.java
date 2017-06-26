package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.RegionMetricaFacade;
import model.RegionMetrica;

@Stateless
public class RegionMetricaFacadeEJB extends AbstractFacade<RegionMetrica> implements RegionMetricaFacade {
    
    @PersistenceContext(unitName = "politweetsPU")
    private EntityManager em;

    public RegionMetricaFacadeEJB() {
        super(RegionMetrica.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
}
