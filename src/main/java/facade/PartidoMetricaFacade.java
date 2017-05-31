package facade;

import java.util.List;
import javax.ejb.Local;
import model.PartidoMetrica;

@Local
public interface PartidoMetricaFacade {

    public void create(PartidoMetrica entity);

    public void edit(PartidoMetrica entity);

    public void remove(PartidoMetrica entity);

    public PartidoMetrica find(Object id);

    public List<PartidoMetrica> findAll();

    public List<PartidoMetrica> findRange(int[] range);

    public int count();

}