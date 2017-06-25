package facade;

import java.util.List;
import javax.ejb.Local;
import model.Region;

@Local
public interface RegionFacade {

    public void create(Region entity);

    public void edit(Region entity);

    public void remove(Region entity);

    public Region find(Object id);

    public List<Region> findAll();

    public List<Region> findRange(int[] range);

    public int count();

    public Region findByCodigo(String codigo);

}