package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.AdminFacade;
import model.Admin;

@Stateless
public class AdminFacadeEJB extends AbstractFacade<Admin> implements AdminFacade {
	
    @PersistenceContext(unitName = "politweetsPU")
    private EntityManager em;

    public AdminFacadeEJB() {
        super(Admin.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

}
