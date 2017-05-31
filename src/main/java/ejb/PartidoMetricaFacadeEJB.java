package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.PartidoMetricaFacade;
import model.PartidoMetrica;

@Stateless
public class PartidoMetricaFacadeEJB extends AbstractFacade<PartidoMetrica> implements PartidoMetricaFacade {

    @PersistenceContext(unitName = "politweetsPU")
    private EntityManager em;

    public PartidoMetricaFacadeEJB() {
        super(PartidoMetrica.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

}
