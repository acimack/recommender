package recommender.src;

/*
 * A node in the decision tree
 */
public interface INode {
    // traverse tree based on attribute values to retrieve decision
    public Object lookupDecision(IAttributeDatum attrVals);

    // print tree
    public void printNode(String leadspace);

    // returns the previous path leading to this INode
    public Object getPrevPath();
}
