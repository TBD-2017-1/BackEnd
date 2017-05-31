package facade;

import java.util.List;
import javax.ejb.Local;
import model.PoliticoMetrica;

@Local
public interface PoliticoMetricaFacade {

    public void create(PoliticoMetrica entity);

    public void edit(PoliticoMetrica entity);

    public void remove(PoliticoMetrica entity);

    public PoliticoMetrica find(Object id);

    public List<PoliticoMetrica> findAll();

    public List<PoliticoMetrica> findRange(int[] range);

    public int count();

}