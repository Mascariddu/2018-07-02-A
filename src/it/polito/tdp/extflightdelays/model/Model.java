package it.polito.tdp.extflightdelays.model;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	ExtFlightDelaysDAO dao;
	List<Airport> vertex;
	List<Arco> edges;
	SimpleWeightedGraph<Airport, DefaultWeightedEdge> grafo;
	HashMap<Integer, Airport> idMap;
	
	List<Airport> best;
	
	public Model() {
		
		dao = new ExtFlightDelaysDAO();
		idMap = new HashMap<Integer, Airport>();
		dao.loadAllAirports(idMap);
		
	}

	public void creaGrafo(double distMedia) {
		// TODO Auto-generated method stub
		grafo = new SimpleWeightedGraph<Airport, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		vertex = dao.loadAirports(distMedia,idMap);
		Graphs.addAllVertices(grafo, vertex);
		
		for(Airport airport : vertex) {
			
			for(Airport airport2 : vertex) {
				
				double peso = dao.getPeso(airport,airport2);
				
				if(!airport.equals(airport2) && grafo.getEdge(airport, airport2) == null && grafo.getEdge(airport2, airport) == null) {
				if(peso > distMedia) {
					Graphs.addEdge(grafo, airport, airport2, peso);
					System.out.println("Aggiunta arco!");
				} else System.out.println("arco non aggiunto!");
				}
			}
		}
		
		System.out.println("#vertici : "+grafo.vertexSet().size());
		System.out.println("#archi : "+grafo.edgeSet().size());
	}

	public List<Airport> vertex() {
		// TODO Auto-generated method stub
		return new ArrayList<Airport>(this.grafo.vertexSet());
	}

	public List<Airport> cercaConnessi(Airport airport) {
		// TODO Auto-generated method stub
		List<Airport> connessi = new ArrayList<Airport>(Graphs.neighborListOf(grafo, airport));
		
		Collections.sort(connessi, new Comparator<Airport>() {

			@Override
			public int compare(Airport o1, Airport o2) {
				// TODO Auto-generated method stub
				DefaultWeightedEdge edge1 = grafo.getEdge(airport, o1);
				DefaultWeightedEdge edge2 = grafo.getEdge(airport, o2);
				
				return -(int)(grafo.getEdgeWeight(edge1) - grafo.getEdgeWeight(edge2));
			}
		});
		
		for(Airport airport2 : connessi)
			System.out.println(airport2.toString()+" distante: "+grafo.getEdgeWeight(grafo.getEdge(airport, airport2)));
		
		return connessi;
	}

	public List<Airport> trovaBest(Airport airport, double miglia) {
		// TODO Auto-generated method stub
		List<Airport> parziale = new ArrayList<Airport>();
		best = new ArrayList<Airport>();
		
		parziale.add(airport);
		best.add(airport);
		
		cerca(parziale,miglia);
		
		return best;
	}

	private void cerca(List<Airport> parziale, double miglia) {
		// TODO Auto-generated method stub
		
		System.out.println("Parziale: "+parziale.size()+" Best: "+best.size()+"\n");
		if(parziale.size() > best.size()) {
			System.out.println("NEW BEST");
			best = new ArrayList<Airport>(parziale);
		} 
		
		Airport last = parziale.get(parziale.size()-1);
		List<Airport> vicini = Graphs.neighborListOf(grafo, last);
		Collections.sort(vicini,new Comparator<Airport>() {

			@Override
			public int compare(Airport o1, Airport o2) {
				// TODO Auto-generated method stub
				DefaultWeightedEdge edge1 = grafo.getEdge(last, o1);
				DefaultWeightedEdge edge2 = grafo.getEdge(last, o2);
				
				return (int)(grafo.getEdgeWeight(edge1) - grafo.getEdgeWeight(edge2));
			}
		});
		
		for(Airport airport : vicini) {
			
			if(getMiglia(parziale,airport) <= miglia && !parziale.contains(airport)) {
				
				System.out.println("AGGIUNGO");
				parziale.add(airport);
				System.out.println("CERCO");
				cerca(parziale, miglia);
				System.out.println("RIMUOVO");
				parziale.remove(parziale.size()-1);
				
			} else { 
				System.out.println("ESCO");
				return;
			}
		}
	}

	public double getMiglia(List<Airport> trovaBest, Airport airport2) {
		// TODO Auto-generated method stub
		double somma = 0.0;
		
		for(int i = 0; i < trovaBest.size(); i++) {
			
			if(i < trovaBest.size()-1) {
			DefaultWeightedEdge edge = grafo.getEdge(trovaBest.get(i), trovaBest.get(i+1));
			somma += grafo.getEdgeWeight(edge);
			}
			
		}
		
		if(airport2 != null) {
		DefaultWeightedEdge edge = grafo.getEdge(trovaBest.get(trovaBest.size()-1), airport2);
		somma += grafo.getEdgeWeight(edge);
		}
		
		return somma;
	}

}
