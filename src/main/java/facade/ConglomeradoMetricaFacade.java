package facade;

import java.util.List;
import javax.ejb.Local;
import model.ConglomeradoMetrica;

@Local
public interface ConglomeradoMetricaFacade {

    public void create(ConglomeradoMetrica entity);

    public void edit(ConglomeradoMetrica entity);

    public void remove(ConglomeradoMetrica entity);

    public ConglomeradoMetrica find(Object id);

    public List<ConglomeradoMetrica> findAll();

    public List<ConglomeradoMetrica> findRange(int[] range);

    public int count();
    
}