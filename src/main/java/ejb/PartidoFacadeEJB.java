package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.PartidoFacade;
import model.Partido;

@Stateless
public class PartidoFacadeEJB extends AbstractFacade<Partido> implements PartidoFacade {

    @PersistenceContext(unitName = "politweetsPU")
    private EntityManager em;

    public PartidoFacadeEJB() {
        super(Partido.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

}
