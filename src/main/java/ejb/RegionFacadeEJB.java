package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.KeywordFacade;
import java.util.List;

import facade.RegionFacade;
import jdk.nashorn.internal.objects.NativeArray;
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

    @Override
    public Region findByCodigo(String codigo){
        List<Region> regiones = this.findAll();
        for (Region k : regiones) {
            if(k.getCodigo().equals(codigo)){
                return k;
            }
        }
        return null;
    }
}
