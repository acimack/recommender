package recommender.sol;

import recommender.src.IAttributeDatum;
import recommender.src.INode;

import java.util.LinkedList;

public class Node<T extends IAttributeDatum> implements INode {
    LinkedList<INode> subtree;
    String attribute;
    Object prevPath;
    Object commonVal;

    /**
     * Constructor for Node
     *
     * @param subtree,   a LinkedList of INodes representing the Node's subtree
     * @param att,       a String representing the attribute held by the Node
     * @param prevPath,  the object choice that led to this Node
     * @param commonVal, the average value for the target attribute at this
     *                   point in the tree
     */
    Node(LinkedList<INode> subtree, String att, Object prevPath, Object commonVal) {
        this.subtree = subtree;
        this.attribute = att;
        this.prevPath = prevPath;
        this.commonVal = commonVal;
    }

    @Override
    public Object lookupDecision(IAttributeDatum attrVals) {
        for (INode node : this.subtree) {
            if (node.getPrevPath().equals(attrVals.getValueOf(this.attribute))) {
                return node.lookupDecision(attrVals);
            }
        }
        return this.commonVal;
    }

    @Override
    public void printNode(String leadspace) {
        if (this.prevPath != null) {
            System.out.println(leadspace + " Edge: " + this.prevPath.toString() + ", Attribute: " + this.attribute);
        } else {
            System.out.println(leadspace + "Attribute: " + this.attribute);
        }

        for (INode node : this.subtree) {
            leadspace = leadspace + "   ";
            node.printNode(leadspace);
        }

    }

    @Override
    public Object getPrevPath() {
        return this.prevPath;
    }

}
