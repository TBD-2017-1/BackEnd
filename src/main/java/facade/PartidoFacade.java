package facade;

import java.util.List;
import javax.ejb.Local;
import model.Partido;

@Local
public interface PartidoFacade {

    public void create(Partido entity);

    public void edit(Partido entity);

    public void remove(Partido entity);

    public Partido find(Object id);

    public List<Partido> findAll();

    public List<Partido> findRange(int[] range);

    public int count();

}