
public class edge {
	
	node source;
	node sink;
	int capacity = 0;
	int flow = 0;
	int residual = 0;
	int real = 1;
	edge anti = null;
	public edge(node source, node sink, int capacity) {
		super();
		this.source = source;
		this.sink = sink;
		this.capacity = capacity;
		this.residual = capacity;
	}
	

}
