package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.PoliticoFacade;
import model.Politico;

@Stateless
public class PoliticoFacadeEJB extends AbstractFacade<Politico> implements PoliticoFacade {	
	
    @PersistenceContext(unitName = "politweetsPU")
    private EntityManager em;

    public PoliticoFacadeEJB() {
        super(Politico.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

}
