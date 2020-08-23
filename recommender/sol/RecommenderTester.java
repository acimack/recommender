package recommender.sol;

import recommender.src.INode;
import tester.Tester;

import java.util.LinkedList;

public class RecommenderTester {

    public RecommenderTester() {
    }

    /**
     * A setup method creating a list of Datums
     *
     * @return a LinkedList of AttributeDatums
     */
    public LinkedList<AttributeDatum> datumList() {
        LinkedList<String> dAttNames = new LinkedList<>();
        dAttNames.add("color");
        dAttNames.add("lowCarb");
        dAttNames.add("highFiber");
        dAttNames.add("likeToEat");


        LinkedList<Object> spinValues = new LinkedList<>();
        spinValues.add("green");
        spinValues.add(true);
        spinValues.add(true);
        spinValues.add(false);

        AttributeDatum dSpinach = new AttributeDatum("spinach", dAttNames, spinValues);

        LinkedList<Object> kaleValues = new LinkedList<>();
        kaleValues.add("green");
        kaleValues.add(true);
        kaleValues.add(true);
        kaleValues.add(true);

        AttributeDatum dKale = new AttributeDatum("kale", dAttNames, kaleValues);

        LinkedList<Object> peasValues = new LinkedList<>();
        peasValues.add("green");
        peasValues.add(false);
        peasValues.add(true);
        peasValues.add(true);

        AttributeDatum dPeas = new AttributeDatum("peas", dAttNames, peasValues);

        LinkedList<Object> carrotValues = new LinkedList<>();
        carrotValues.add("orange");
        carrotValues.add(false);
        carrotValues.add(false);
        carrotValues.add(false);

        AttributeDatum dCarrot = new AttributeDatum("carrot", dAttNames, carrotValues);

        LinkedList<Object> lettValues = new LinkedList<>();
        lettValues.add("green");
        lettValues.add(true);
        lettValues.add(false);
        lettValues.add(true);

        AttributeDatum dLettuce = new AttributeDatum("lettuce", dAttNames, lettValues);

        LinkedList<AttributeDatum> datumList = new LinkedList<>();
        datumList.add(dSpinach);
        datumList.add(dKale);
        datumList.add(dPeas);
        datumList.add(dCarrot);
        datumList.add(dLettuce);

        return datumList;

    }

    public INode createTree() {
        LinkedList<INode> leftLeafSub = new LinkedList<>();
        leftLeafSub.add(new Leaf(false, true));
        leftLeafSub.add(new Leaf(true, false));

        LinkedList<INode> rightLeafSub = new LinkedList<>();
        rightLeafSub.add(new Leaf(true, true));
        rightLeafSub.add(new Leaf(false, false));

        LinkedList<INode> leftBotNode = new LinkedList<>();
        leftBotNode.add(new Node(leftLeafSub, "lowCarb", "green", true));

        INode leftMidN = new Node(leftBotNode, "color", true, true);

        LinkedList<INode> leftMidNode = new LinkedList<>();
        leftMidNode.add(leftMidN);

        INode rightMidN = new Node(rightLeafSub, "lowCarb", false, false);

        LinkedList<INode> rightMidNode = new LinkedList<>();
        rightMidNode.add(rightMidN);

        LinkedList<INode> bigSubtree = new LinkedList<>();
        bigSubtree.add(rightMidN);
        bigSubtree.add(leftMidN);

        INode bigKahuna = new Node(bigSubtree, "likeToEat", null, true);
        return bigKahuna;
    }

    /**
     * A setup method to create a Dataset
     *
     * @return an AttributeDataset of AttributeDatums
     */
    public AttributeDataset<AttributeDatum> createDataset() {
        LinkedList<String> dAttNames = new LinkedList<>();
        dAttNames.add("color");
        dAttNames.add("lowCarb");
        dAttNames.add("highFiber");
        dAttNames.add("likeToEat");


        AttributeDataset<AttributeDatum> dataset = new AttributeDataset<>(dAttNames, datumList());
        return dataset;

    }

    /**
     * A setup method to create a Dataset
     *
     * @return an AttributeDataset of AttributeDatums
     */
    public AttributeDataset<AttributeDatum> createDatasetLowCarb() {
        LinkedList<String> dAttNames = new LinkedList<>();
        dAttNames.add("color");
        dAttNames.add("lowCarb");
        dAttNames.add("highFiber");
        dAttNames.add("likeToEat");

        LinkedList<AttributeDatum> newDatumList = new LinkedList<>();
        newDatumList.add(datumList().get(0));
        newDatumList.add(datumList().get(1));
        newDatumList.add(datumList().get(4));


        AttributeDataset<AttributeDatum> dataset = new AttributeDataset<>(dAttNames, newDatumList);
        return dataset;

    }

    /**
     * A setup method to create a Dataset
     *
     * @return an AttributeDataset of AttributeDatums
     */
    public AttributeDataset<AttributeDatum> createDatasetComVal() {
        LinkedList<String> dAttNames = new LinkedList<>();
        dAttNames.add("color");
        dAttNames.add("lowCarb");
        dAttNames.add("highFiber");
        dAttNames.add("likeToEat");

        LinkedList<AttributeDatum> newDatumList = new LinkedList<>();
        newDatumList.add(datumList().get(0));
        newDatumList.add(datumList().get(1));
        newDatumList.add(datumList().get(3));
        newDatumList.add(datumList().get(4));


        AttributeDataset<AttributeDatum> dataset = new AttributeDataset<>(dAttNames, newDatumList);
        return dataset;

    }

    //Tests for printTree
    public void testPrintTree(Tester t) {
        TreeGenerator tree = new TreeGenerator(createDataset());
        tree.buildClassifier("highFiber");
        tree.printTree();
    }

    //Tests for getValueOf
    public void testGetValueOf(Tester t) {
        t.checkExpect(datumList().get(0).getValueOf("highFiber"), true);
        t.checkExpect(datumList().get(0).getValueOf("lowCarb"), true);
        t.checkExpect(datumList().get(3).getValueOf("color"), "orange");

        t.checkException(new RuntimeException("This is not an attribute"), datumList().get(0), "getValueOf", "texture");
    }

    //Tests for getAttributes
    public void testGetAttributes(Tester t) {
        LinkedList<String> dAttNames = new LinkedList<>();
        dAttNames.add("color");
        dAttNames.add("lowCarb");
        dAttNames.add("highFiber");
        dAttNames.add("likeToEat");

        t.checkExpect(createDataset().getAttributes(), dAttNames);
    }

    //Tests for allSameValue
    public void testAllSameValue(Tester t) {
        t.checkExpect(createDataset().allSameValue("highFiber"), false);
        t.checkExpect(createDatasetLowCarb().allSameValue("lowCarb"), true);
        t.checkExpect(createDatasetLowCarb().allSameValue("highFiber"), false);
        t.checkExpect(createDatasetLowCarb().allSameValue("color"), true);
    }

    //Tests for size
    public void testSize(Tester t) {
        t.checkExpect(createDataset().size(), 5);
        t.checkExpect(createDatasetLowCarb().size(), 3);
    }

    //Tests getDataSet
    public void testGetDataSet(Tester t) {
        LinkedList<AttributeDatum> newDatumList = new LinkedList<>();
        newDatumList.add(datumList().get(0));
        newDatumList.add(datumList().get(1));
        newDatumList.add(datumList().get(4));

        t.checkExpect(createDataset().getDataset(), datumList());
        t.checkExpect(createDatasetLowCarb().getDataset(), newDatumList);
    }

    //Tests for partition
    public void testPartition(Tester t) {
        LinkedList<String> dAttNames = new LinkedList<>();
        dAttNames.add("color");
        dAttNames.add("lowCarb");
        dAttNames.add("highFiber");
        dAttNames.add("likeToEat");

        LinkedList<AttributeDatum> partitionList1 = new LinkedList<>();
        partitionList1.add(datumList().get(4));
        partitionList1.add(datumList().get(2));
        partitionList1.add(datumList().get(1));

        AttributeDataset<AttributeDatum> partitionDS1 = new AttributeDataset<AttributeDatum>(dAttNames, partitionList1);

        LinkedList<AttributeDatum> partitionList2 = new LinkedList<>();
        partitionList2.add(datumList().get(3));
        partitionList2.add(datumList().get(0));
        AttributeDataset<AttributeDatum> partitionDS2 = new AttributeDataset<AttributeDatum>(dAttNames, partitionList2);

        LinkedList<AttributeDataset> partitions = new LinkedList<>();
        partitions.add(partitionDS1);
        partitions.add(partitionDS2);

        t.checkExpect(createDataset().partition("likeToEat"), partitions);
    }

    //Tests for getSharedValue
    public void testGetSharedValue(Tester t) {
        t.checkExpect(createDataset().partition("color").get(0).getSharedValue("color"), "orange");
        t.checkExpect(createDataset().partition("color").get(1).getSharedValue("color"), "green");

    }

    //Tests for mostCommonValue
    public void testMostCommonValue(Tester t) {
        t.checkExpect(createDatasetLowCarb().mostCommonValue("lowCarb"), true);
        t.checkExpect(createDataset().mostCommonValue("likeToEat"), true);
        t.checkExpect(createDataset().mostCommonValue("color"), "green");
        t.checkExpect(createDataset().mostCommonValue("highFiber"), true);
        t.checkExpect(createDatasetComVal().mostCommonValue("likeToEat"), false);
    }

    //Tests for lookupDecision
    public void testLookUpDecision(Tester t) {
        LinkedList<String> dAttNames = new LinkedList<>();
        dAttNames.add("color");
        dAttNames.add("lowCarb");
        dAttNames.add("highFiber");
        dAttNames.add("likeToEat");

        LinkedList<Object> yamValues = new LinkedList<>();
        yamValues.add("orange");
        yamValues.add(false);
        yamValues.add(false);
        yamValues.add(false);

        AttributeDatum yam = new AttributeDatum("yam", dAttNames, yamValues);

        t.checkExpect(createTree().lookupDecision(yam), false);

        LinkedList<Object> broccoliValues = new LinkedList<>();
        broccoliValues.add("green");
        broccoliValues.add(false);
        broccoliValues.add(true);
        broccoliValues.add(true);

        AttributeDatum broccoli = new AttributeDatum("broccoli", dAttNames, broccoliValues);

        t.checkExpect(createTree().lookupDecision(broccoli), true);
    }


    //Tests for getPrevPath
    public void testGetPrevPath(Tester t) {
        LinkedList<INode> leftLeafSub = new LinkedList<>();
        leftLeafSub.add(new Leaf(false, true));
        leftLeafSub.add(new Leaf(true, false));

        LinkedList<INode> leftBotNode = new LinkedList<>();
        leftBotNode.add(new Node(leftLeafSub, "lowCarb", "green", true));

        INode tree = createTree();
        t.checkExpect(tree.getPrevPath(), null);
        t.checkExpect(leftBotNode.get(0).getPrevPath(), "green");
        t.checkExpect(leftLeafSub.get(0).getPrevPath(), true);
    }


    public static void main(String[] args) {
        Tester.run(new RecommenderTester());
    }
}
