package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.KeywordFacade;
import java.util.List;
import jdk.nashorn.internal.objects.NativeArray;
import model.Keyword;

@Stateless
public class KeywordFacadeEJB extends AbstractFacade<Keyword> implements KeywordFacade {	
	
    @PersistenceContext(unitName = "politweetsPU")
    private EntityManager em;

    public KeywordFacadeEJB() {
        super(Keyword.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
    @Override
    public Keyword findByValue(Keyword keyword){
        List<Keyword> keywords = this.findAll();
        for (Keyword k : keywords) {
            if(k.getValue().equals(keyword.getValue())){
                return k;
            }
        }
        return null;
    }
}
