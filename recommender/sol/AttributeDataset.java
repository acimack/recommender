package recommender.sol;

import recommender.src.IAttributeDataset;
import recommender.src.IAttributeDatum;

import java.util.LinkedList;

public class AttributeDataset<T extends IAttributeDatum> implements IAttributeDataset {

    public LinkedList<T> dataset;
    public LinkedList<String> attributes;

    /**
     * Constructor for AttributeDataset
     *
     * @param attributes, a LinkedList of Strings, representing the
     *                    names of attributes
     * @param dS,         a LinkedList of datums
     */
    AttributeDataset(LinkedList<String> attributes, LinkedList<T> dS) {
        this.dataset = dS;
        this.attributes = attributes;
    }

    @Override
    public LinkedList<String> getAttributes() {
        return this.attributes;
    }

    @Override
    public boolean allSameValue(String ofAttribute) {
        Object holder = this.dataset.get(0).getValueOf(ofAttribute);
        for (T datum : this.dataset) {
            if (!datum.getValueOf(ofAttribute).equals(holder)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return this.dataset.size();
    }

    @Override
    public LinkedList<T> getDataset() {
        return this.dataset;
    }

    @Override
    public LinkedList<IAttributeDataset> partition(String onAttribute) {
        LinkedList<Object> seenValues = new LinkedList<Object>();
        LinkedList<IAttributeDataset> groupedDatums = new LinkedList<>();
        for (T datum : this.dataset) {
            if (!seenValues.contains(datum.getValueOf(onAttribute))) {
                LinkedList<T> newList = new LinkedList<>();
                newList.addFirst(datum);
                seenValues.addFirst(datum.getValueOf(onAttribute));
                groupedDatums.addFirst(new AttributeDataset(this.attributes, newList));
            } else {
                int i = seenValues.indexOf(datum.getValueOf(onAttribute));
                groupedDatums.get(i).getDataset().addFirst(datum);
            }
        }
        return groupedDatums;
    }

    @Override
    public Object getSharedValue(String ofAttribute) {
        return this.dataset.get(0).getValueOf(ofAttribute);
    }

    @Override
    public Object mostCommonValue(String ofAttribute) {
        LinkedList<IAttributeDataset> groups = this.partition(ofAttribute);
        int max = 0;
        Object commonVal = null;
        for (IAttributeDataset set : groups) {
            if (set.size() >= max) {
                max = set.size();
                commonVal = set.getSharedValue(ofAttribute);
            }
        }
        return commonVal;
    }
}
