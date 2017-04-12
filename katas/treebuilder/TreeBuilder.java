package org.gmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TreeBuilder {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int t1[][] = { { 1, 2 } };
        int t2[][] = { { 1, 2 }, { 2, 3 } };
        int t3[][] = { { 1, 2 }, { 2, 3 }, { 1, 4 } };
        int t4[][] = { { 1, 2 }, { 3, 5 }, { 2, 3 } };
        int t5[][] = { { 1, 2 }, { 2, 3 }, { 5, 1 } };
        int t6[][] = { { 1, 2 }, { 2, 3 }, { 2, 5 } };
        System.out.println("t1:\n" + printTree(buildTree(t1)));
        System.out.println("t2:\n" + printTree(buildTree(t2)));
        System.out.println("t3:\n" + printTree(buildTree(t3)));
        System.out.println("t4:\n" + printTree(buildTree(t4)));
        System.out.println("t5:\n" + printTree(buildTree(t5)));
        System.out.println("t6:\n" + printTree(buildTree(t6)));
    }

    static class Node {
        private int value;

        private Node parent;
        private Node leftChild;
        private Node rightChild;

        public Node(int value) {
            this.value = value;
            parent = null;
            leftChild = null;
            rightChild = null;
        }

        public int value() {
            return value;
        }

        public boolean hasParent() {
            return parent != null;
        }

        public void setParent(Node parent) {
            if (this.parent == null) {
                this.parent = parent;
            } else {
                throw new AssertionError("Parent already set");
            }
        }

        public void setChild(Node child) {
            if (this.leftChild == null) {
                leftChild = child;
            } else if (this.rightChild == null) {
                rightChild = child;
            } else {
                throw new AssertionError("Children already set");
            }
        }
        
        public List<Node> getChildren() {
            List<Node> children = new ArrayList<>();
            children.add(leftChild);
            children.add(rightChild);
            return children;
        }

        @Override
        public String toString() {
            return "(" + value + ") -> ("
                    + (leftChild != null ? leftChild.toString() : "NULL")
                    + ", "
                    + (rightChild != null ? rightChild.toString() : "NULL")
                    + ")";
        }
    }

    public static String printTree(Node root) {
        StringBuilder builder = new StringBuilder();
        builder.append(root.value());
        builder.append("\n");
        List<Node> children = root.getChildren();
        while(!children.isEmpty()){
            List<Node> nextChildren = new ArrayList<>();
            for(Node child: children){
                if(child != null){
                    builder.append(child.value());
                    nextChildren.addAll(child.getChildren());
                } else {
                    builder.append("E");
                }
                builder.append(" ");                
            }
            builder.append("\n");
            children = nextChildren;            
        }
        
        return builder.toString();
    }

    public static Node buildTree(int[][] input) {
        HashMap<Integer, Node> nodes = new HashMap<>();
        for (int i = 0; i < input.length; i++) {
            int parentValue = input[i][0];
            int childValue = input[i][1];

            Node parentNode = nodes.get(parentValue);
            Node childNode = nodes.get(childValue);

            if(parentNode == null){
                parentNode = new Node(parentValue);
                nodes.put(parentNode.value(), parentNode);
            }
            
            if(childNode == null){
                childNode = new Node(childValue);
                nodes.put(childNode.value(), childNode);
            }
            
            parentNode.setChild(childNode);
            childNode.setParent(parentNode);
        }

        Node root = null;
        for (Node node : nodes.values()) {
            if (!node.hasParent()) {
                root = node;
                break;
            }
        }

        return root;

    }

}
