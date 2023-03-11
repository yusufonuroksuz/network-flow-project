import java.util.ArrayList;

public class node {
	
	String id;
	ArrayList<edge> edges = new ArrayList<>();
	boolean used = false;
	node parent;
	edge parentedge;
	int lenght_to_parent;
	public node(String id) {
		this.id = id;
	}
	public void add_edge(edge edge) {
		this.edges.add(edge);
	}
	
	
}
