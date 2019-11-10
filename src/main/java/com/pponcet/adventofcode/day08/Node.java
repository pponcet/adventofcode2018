package com.pponcet.adventofcode.day08;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final int numberNodes;
    private final int sumMetadata;
    private final List<Node> nodes;

    public Node(int numberNodes, int sumMetadata) {
        this.numberNodes = numberNodes;
        this.sumMetadata = sumMetadata;
        this.nodes = new ArrayList<>();
    }

    public int getNumberNodes() {
        return numberNodes;
    }

    public int getSumMetadata() {
        return sumMetadata;
    }

    public void add(Node node){
        nodes.add(node);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        if (getNumberNodes() != node.getNumberNodes()) return false;
        return getSumMetadata() == node.getSumMetadata();

    }

    @Override
    public int hashCode() {
        int result = getNumberNodes();
        result = 31 * result + getSumMetadata();
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "numberNodes=" + numberNodes +
                ", sumMetadata=" + sumMetadata +
                '}';
    }
}
