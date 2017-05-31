package facade;

import java.util.List;
import javax.ejb.Local;
import model.Metrica;

@Local
public interface MetricaFacade {

    public void create(Metrica entity);

    public void edit(Metrica entity);

    public void remove(Metrica entity);

    public Metrica find(Object id);

    public List<Metrica> findAll();

    public List<Metrica> findRange(int[] range);

    public int count();

    public Metrica findByName(String name);

}