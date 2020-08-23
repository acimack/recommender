package recommender.sol;

import recommender.src.IAttributeDataset;
import recommender.src.IAttributeDatum;
import recommender.src.IGenerator;
import recommender.src.INode;

import java.util.LinkedList;
import java.util.Random;

public class TreeGenerator<T extends IAttributeDatum> implements IGenerator {
    AttributeDataset<T> dataset;
    INode currentTree = null;

    /**
     * Constructor for TreeGenerator
     *
     * @param initTrainingData, an AttributeDataset for the TreeGenerator to
     *                          generate trees off of
     */
    public TreeGenerator(AttributeDataset<T> initTrainingData) {
        this.dataset = initTrainingData;
    }

    @Override
    public INode buildClassifier(String targetAttr) {

        LinkedList<String> attributeList = new LinkedList<>();
        attributeList.addAll(this.dataset.getAttributes());
        attributeList.remove(targetAttr);
        INode tree = buildHelper(targetAttr, attributeList, this.dataset, null);
        this.currentTree = tree;
        return tree;
    }

    @Override
    public Object lookupRecommendation(IAttributeDatum forVals) {
        return this.currentTree.lookupDecision(forVals);
    }

    @Override

    public void printTree() {
        this.currentTree.printNode("");

    }

    public INode buildHelper(String targetAttr, LinkedList<String> availableAttrs, IAttributeDataset<T> currentDS, Object prevPath) {
        Random random = new Random();
        if (currentDS.allSameValue(targetAttr)) {
            return new Leaf(currentDS.getSharedValue(targetAttr), prevPath);
        } else {
            LinkedList<INode> subtree = new LinkedList<>();
            int index = 0;
            if (availableAttrs.size() != 0) {
                index = random.nextInt(availableAttrs.size());
            } else {
                return new Leaf(currentDS.mostCommonValue(targetAttr), prevPath);
            }
            String currentNodeAttr = availableAttrs.get(index);
            LinkedList<String> availableAttrsCopy = new LinkedList<>();
            availableAttrsCopy.addAll(availableAttrs);
            availableAttrsCopy.remove(currentNodeAttr);

            LinkedList<IAttributeDataset<T>> datasetPartitions = currentDS.partition(currentNodeAttr);
            for (IAttributeDataset DS : datasetPartitions) {
                subtree.add(buildHelper(targetAttr, availableAttrsCopy, DS, DS.getSharedValue(currentNodeAttr)));
            }
            return new Node(subtree, currentNodeAttr, prevPath, currentDS.mostCommonValue(targetAttr));
        }
    }
}
