package facade;

import java.util.List;
import javax.ejb.Local;
import model.Admin;

@Local
public interface AdminFacade {

    public void create(Admin entity);

    public void edit(Admin entity);

    public void remove(Admin entity);

    public Admin find(Object id);

    public List<Admin> findAll();

    public List<Admin> findRange(int[] range);

    public int count();

}