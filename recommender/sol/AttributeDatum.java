package recommender.sol;

import recommender.src.IAttributeDatum;

import java.util.LinkedList;

public class AttributeDatum implements IAttributeDatum {

    private String name;

    private LinkedList<String> attributeNames;

    private LinkedList<Object> attributeValues;

    AttributeDatum(String name, LinkedList<String> attN, LinkedList<Object> attV) {
        this.name = name;
        this.attributeNames = attN;
        this.attributeValues = attV;
    }

    @Override
    public Object getValueOf(String attributeName) {
        if (this.attributeNames.contains(attributeName)) {
            int i = this.attributeNames.indexOf(attributeName);
            return this.attributeValues.get(i);
        } else {
            throw new RuntimeException("This is not an attribute");
        }

    }
}
