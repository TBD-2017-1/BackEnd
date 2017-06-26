package facade;

import java.util.List;
import javax.ejb.Local;
import model.RegionMetrica;

@Local
public interface RegionMetricaFacade {
    
    public void create(RegionMetrica entity);

    public void edit(RegionMetrica entity);

    public void remove(RegionMetrica entity);

    public RegionMetrica find(Object id);

    public List<RegionMetrica> findAll();

    public List<RegionMetrica> findRange(int[] range);

    public int count();
}
