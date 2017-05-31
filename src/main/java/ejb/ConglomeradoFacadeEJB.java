package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.ConglomeradoFacade;
import model.Conglomerado;

@Stateless
public class ConglomeradoFacadeEJB extends AbstractFacade<Conglomerado> implements ConglomeradoFacade {
	
    @PersistenceContext(unitName = "politweetsPU")
    private EntityManager em;

    public ConglomeradoFacadeEJB() {
        super(Conglomerado.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

}
