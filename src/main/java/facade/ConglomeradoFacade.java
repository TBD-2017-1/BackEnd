package facade;

import java.util.List;
import javax.ejb.Local;
import model.Conglomerado;

@Local
public interface ConglomeradoFacade {

    public void create(Conglomerado entity);

    public void edit(Conglomerado entity);

    public void remove(Conglomerado entity);

    public Conglomerado find(Object id);

    public List<Conglomerado> findAll();

    public List<Conglomerado> findRange(int[] range);

    public int count();

}