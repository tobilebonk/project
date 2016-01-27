package java4bioinf.rna2d;

import de.tobilebonk.nucleotide3D.Residue;
import de.tobilebonk.utils.ComputationUtils;

import java.io.IOException;
import java.util.*;

/**
 * a simple graph
 * Daniel Huson, 11.2015
 */
public class Graph {
    private int numberOfNodes;
    private int[][] edges;

    public Graph() {
        edges = new int[0][2];
        numberOfNodes = 0;
    }

    public Graph(int[]... edges) {
        setAllEdges(edges);
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public int getNumberOfEdges() {
        return edges.length;
    }

    /**
     * gets array of edges, do not modify any edge here
     *
     * @return edges
     */
    public int[][] getEdges() {
        return edges;
    }

    /**
     * get an edge
     * @param e edge number
     * @return edge
     */
    public int[] getEdge(int e) {
        return edges[e];
    }

    /**
     * set all edges
     * Infers the number of nodes as v+1, where v is the highest node index encountered
     * @param edges all edges
     */
    public void setAllEdges(int[]... edges) {
        if (this.edges.length != edges.length)
            this.edges = new int[edges.length][];
        System.arraycopy(edges, 0, this.edges, 0, edges.length);
        numberOfNodes = 0;
        for (int[] edge : edges) {
            numberOfNodes = Math.max(numberOfNodes, edge[0] + 1);
            numberOfNodes = Math.max(numberOfNodes, edge[1] + 1);
        }
    }

    /**
     * parse RNA secondary structure bracket notation and sets the graph accordingly
     *
     * @param notation RNA structure bracket notation
     * @throws IOException
     */
    public void parseNotation(String notation) throws IOException {
        int numberOfNodes = notation.length();
        int numberOfPairs = 0;
        for (int i = 0; i < notation.length(); i++) {
            if (notation.charAt(i) == '(')
                numberOfPairs++;
        }
        int[][] edges = new int[numberOfNodes - 1 + numberOfPairs][2];
        for (int i = 0; i < numberOfNodes - 1; i++) {
            edges[i][0] = i;
            edges[i][1] = i + 1;
        }

        int numberOfEdges = numberOfNodes - 1;

        final BitSet closed=new BitSet();
        for (int left = 0; left < notation.length(); left++) {
            if (notation.charAt(left) == '(') {
                int open = 1;
                for (int right = left + 1; open > 0 && right < notation.length(); right++) {
                    switch (notation.charAt(right)) {
                        case '(':
                            open++;
                            break;
                        case ')':
                            open--;
                            if (open == 0) {
                                edges[numberOfEdges][0] = left;
                                edges[numberOfEdges][1] = right;
                                closed.set(right);
                                numberOfEdges++;
                            }
                            break;
                    }
                }
                if (open > 0)
                    throw new IOException("Brackets unbalanced, "+open+" still open at end of text");
            }
            else if(notation.charAt(left)==')' && !closed.get(left))
                throw new IOException("Brackets unbalanced, unexpected close at position: "+left);
        }
        setAllEdges(edges);
    }

    public void createEdgesFromResidueList(List<Residue> residues){

        List<int[]> edges = new ArrayList<>();
        numberOfNodes = residues.size();
        Set<Integer> foundWCPairIndices = new HashSet<>();

        boolean found = false;

        for (int i = 0; i < numberOfNodes - 1; i++) {
            int[] edge = new int[2];
            edge[0] = i;
            edge[1] = i + 1;
            edges.add(edge);
        }

        for(int i = 0; i < residues.size(); i++){
            Residue currentResidue = residues.get(i);
            for(int j = i + 1; j < residues.size(); j++){
                Residue comparedResidue = residues.get(j);
                if (!found && ComputationUtils.computeIsResidueConnectedToResidue(currentResidue, comparedResidue)) {
                    int[] edge = new int[2];
                    edge[0] = i;
                    edge[1] = j;
                    if(!foundWCPairIndices.contains(j)){
                        edges.add(edge);
                        foundWCPairIndices.add(j);
                        found = true;
                        break;
                    }
                }
                if(found){
                    found = false;
                    continue;
                }

            }
        }
        int[][] edgesArray = new int[edges.size()][];
        for(int i = 0; i < edgesArray.length; i++){
            edgesArray[i] = edges.get(i);
        }
        setAllEdges(edgesArray);
    }
}

