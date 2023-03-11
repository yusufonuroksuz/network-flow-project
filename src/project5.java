import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;



public class project5 {
	
	public static void edges(node source, node sink, int cap) {
		edge edge1 = new edge(source, sink, cap);
		source.add_edge(edge1);
		edge edge2 = new edge(sink, source, cap);
		sink.add_edge(edge2);
		edge2.residual = 0;
		edge2.real = 0;
		edge1.anti = edge2;
		edge2.anti = edge1;
	}
	
	public static int path(node source, node sink, HashMap<String, node> nodes) {
		boolean anyPath = false;
		for (Entry<String, node> node : nodes.entrySet()) node.getValue().used = false;
		Queue<node> q = new LinkedList<>();
		q.add(source);
		source.used = true;
		source.parent = null;
		while (!q.isEmpty() && !anyPath) {
			node node = q.poll();
			for (edge edge : node.edges) {
				if (edge.residual == 0) continue;
				if (edge.sink.id.equals(sink.id)) {
					sink.parent = edge.source;
					sink.parentedge = edge;
					sink.lenght_to_parent = edge.residual;
					anyPath = true;
					break;
				}
				if (edge.sink.used == true) continue;
				q.add(edge.sink);
				edge.sink.used = true;
				edge.sink.parent = edge.source;
				edge.sink.parentedge = edge;
				edge.sink.lenght_to_parent = edge.residual;
			}
		}
		
		
		
		
		if (!anyPath) return 0;
		int min = 2147000000;
		node current = sink;
		while (!current.id.equals("source")) {
			min = Math.min(min, current.lenght_to_parent);
			current = current.parent;
		}
		current = sink;
		while (!current.id.equals("source")) {
			current.parentedge.flow += min;
			current.parentedge.residual -= min;
			current.parentedge.anti.flow -= min;
			current.parentedge.anti.residual += min;
			current = current.parent;
		}
		return min;
	}

	public static void main(String[] args) throws IOException {
		FileReader inputFile = new FileReader(args[0]);
		BufferedReader input = new BufferedReader(inputFile);
		File outputFile = new File(args[1]);
		FileWriter output = new FileWriter(outputFile);
		
		HashMap<String, node> nodes = new HashMap<>();
		node source = new node("source");
		nodes.put("source", source);
		
		int city_no = Integer.parseInt(input.readLine().split(" ")[0]);

		String[] capacities = input.readLine().split(" ");
		
		for (int i = 0; i < 6; i++) {
			String[] line = input.readLine().split(" ");
			String name = line[0];
			node s = new node(name);
			nodes.put(name, s);
			edges(source, s, Integer.parseInt(capacities[i]));
			
			for (int j = 1; j < line.length; j = j+2) {
				String id = line[j];
				int cap = Integer.parseInt(line[j+1]);
				if (nodes.containsKey(id)) {
					node adj_node = nodes.get(id);
					edges(s, adj_node, cap);
				}
				else {
					node adj_node = new node(id);
					nodes.put(id, adj_node);
					edges(s, adj_node, cap);
				}
			}
			
		}
		
		for (int i = 0; i < city_no; i++) {
			String[] line = input.readLine().split(" ");
			String name = line[0];
			
			if (nodes.containsKey(name)) {
				node s = nodes.get(name);
				for (int j = 1; j < line.length; j = j+2) {
					String id = line[j];
					int cap = Integer.parseInt(line[j+1]);
					if (nodes.containsKey(id)) {
						node adj_node = nodes.get(id);
						edges(s, adj_node, cap);
					}
					else {
						node adj_node = new node(id);
						nodes.put(id, adj_node);
						edges(s, adj_node, cap);
					}
				}
			}
			else {
				node s = new node(name);
				nodes.put(name, s);
				for (int j = 1; j < line.length; j = j+2) {
					String id = line[j];
					int cap = Integer.parseInt(line[j+1]);
					if (nodes.containsKey(id)) {
						node adj_node = nodes.get(id);
						edges(s, adj_node, cap);
					}
					else {
						node adj_node = new node(id);
						nodes.put(id, adj_node);
						edges(s, adj_node, cap);
					}
				}
			}
		}
		
		
		int flow = 0;
		while (true) {
			int c = path(source, nodes.get("KL"), nodes);
			if (c == 0) break;
			flow += c;
		}
		System.out.println(flow);
		output.write(flow+"\n");
		input.close();
		output.close();
		
	}

}
