package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.RegionFacade;
import model.Region;

@Stateless
public class RegionFacadeEJB extends AbstractFacade<Region> implements RegionFacade {
    
    @PersistenceContext(unitName = "politweetsPU")
    private EntityManager em;

    public RegionFacadeEJB() {
        super(Region.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
}
