package recommender.sol;

import recommender.src.IAttributeDatum;
import recommender.src.INode;

public class Leaf implements INode {
    Object value;
    Object prevPath;

    Leaf(Object val, Object prevPath) {
        this.prevPath = prevPath;
        this.value = val;
    }

    @Override
    public Object lookupDecision(IAttributeDatum attrVals) {
        return this.value;
    }

    @Override
    public void printNode(String leadspace) {
        System.out.println(leadspace + " Edge: " + this.prevPath.toString() + " -> Leaf Value: " + this.value.toString());

    }

    @Override
    public Object getPrevPath() {
        return this.prevPath;
    }

}
