package facade;

import java.util.List;
import javax.ejb.Local;
import model.Politico;

@Local
public interface PoliticoFacade {

    public void create(Politico entity);

    public void edit(Politico entity);

    public void remove(Politico entity);

    public Politico find(Object id);

    public List<Politico> findAll();

    public List<Politico> findRange(int[] range);

    public int count();

}