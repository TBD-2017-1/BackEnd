package ejb;

import PoliTweetsCL.Neo4J.GraphAPI;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class GraphEJB {
    @EJB
    GraphAPI graphAPI;


}
