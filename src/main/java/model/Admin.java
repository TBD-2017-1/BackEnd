package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the admin database table.
 * 
 */
@Entity
@Table(name="admin")
@NamedQuery(name="Admin.findAll", query="SELECT a FROM Admin a")
public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;
    
    //Atributes
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private int id;

    @Column(name="username", nullable=false, unique=true, length=45)
    private String username;

    @Column(name="password", nullable=false, length=45)
    private String password;

    //Methods
    public Admin() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
	
    public boolean verify(String username, String password){
        if(this.username.equals(username) && this.password.equals(password)){
            return true;
        }
        return false;
    }
}