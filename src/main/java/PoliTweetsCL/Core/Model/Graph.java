package PoliTweetsCL.Core.Model;

import com.google.gson.Gson;

import java.util.List;
import java.util.Set;

public class Graph {
    List<Nodo> nodes;
    List<Relacion> edges;

    class Nodo{
        String id;
        Set<String> tipo;
        String cuenta;
        String nombre;
    }

    class Relacion{
        String id;
        String source;
        String target;
    }
}
