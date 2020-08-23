When given a dataset, our code takes a row from the dataset and creates an AttributeDatum. The AttributeDatum is comprised of the name associated with the datum, a list of the names of the attributes of the datum, and a list of the values of the datum for each attribute. Then, our code compiles an AttributeDataset, which is a list of the datums as well as a list of their attributes. From there, the code generates a tree in TreeGenerator implemented areound a specific attribute, and the code can then use this tree to determine the value of a new datum for this attribute. The tree is comprised of Nodes, which contain the attribute being switched on, the path that led to the attribute, the common value of the for the target attribute at the current point in the tree, and the subtree of Nodes. The subtree is a list of nodes that represents the group of datums with shared values directly below this node in the tree. When all of the attributes have been switched on and there are are no more datums grouped within subtrees, the code returns a Leaf containing the value of the decision. 

It is possible that our code incorrectly builds trees. We believe this may be a possibility because of the way our printNode looks. It appears that we are not fully switching on all attributes within each branch of the tree. A TA did look at our code in the debugger, and she believes that we are correctly compiling the trees from our data, so this may or may not be the case. If we are actually not fully switching on all attributes, then that would impact our ability to accurately make decisions for a set of values. Also, we are not able to test lookupRecommendation because it would require us creating a TreeGenerator, which would mean that we would create a random tree, and we'd be unable to accurately test repeated results.



(female, male) (.28, 1), (.06, 1), (.24, .27), (.15, 1), (.1, 1)
More males are consistently hired.

(.06, .16), (.0, .08), (0.0, .05), (0.0, 0.08), (.01, .1)
The ratios are lower, yet more men are still hired than women.

How do you think each training dataset (also presented on the Google sheet) affects the hiring bias?

Because the training data sets have fewer women than men, and out of the women included in the training set, there are only two that were hired, it is difficult for new women to be hired unless they fit the very specific guidelines set by the values of the two hired women.

How could you modify your recommender system to prevent gender bias from arising? Identify an improvement to the training data and/or algorithm that could help prevent unfair outcomes.

In regards to the data, more women whould be added to the list of candidates, especially more examples of women who were hired. That would allow for a wider range of characteristics to be used as guidelines for hiring.

How does the approach your code used to choose which attribute to split on impact the resulting bias, if at all?

Our code randomly selects the attribute it splits on everytime a new node is constructed, which should limit the resulting bias in that it places an equal worth on all attributes. 

If your hiring rates vary each time you build a new classifier, why does this occur? If we fix the algorithm to choose the same attribute to split on each time, could we eliminate the bias?

Hiring rates vary with every new classifier as a result of randomly generated tree, which is different everytime the classifier is built. Even if we made it so the algorithm switched on the same attributes in the same order each time, we would not have eliminated the bias because the bias also exists within the dataset. 

Given the company’s current skewed demographics, outline the extent to which ensuring fairness in this hiring system is the responsibility of the engineers, hiring manager, or someone else.

As for the engineers, they only have control over the current algorithm. They should do what is in their power to avoid allowing for biases within their code. The engineers should allow for their code to clearly demonstrate the attributes and the reasoning behind the code's decision making, in order to enable the algorithm to be more easily monitored for bias. It is the hiring manager's job to provide a dataset that does not already contain bias. 

Do you still see evidence of hiring bias?

Yes, but more women overall are being hired.

Proponents of algorithmic hiring argue that these systems have the potential to make the hiring process more efficient and remove some human biases from hiring decisions. Other than potential bias, what criteria do you think an organization should consider when deciding whether to use an algorithmic system for hiring?

They should take into consideration both quantifiable and qualitative factors. Regarding the quantitative factors, it is important that the dataset the algorithm is given contains positive examples of underrepresented groups being hired. Additionally, qualitative factors are difficult to guage within an algorithm, so the hiring managers must also pay attention to the importance of softskills. 

The recommender system you created could be used in a wide variety of contexts. You’ve observed the potential of the system to generate biased results. Do you the programmer have any responsibility to prevent potentially harmful uses of your code? What is one step that you (the programmer) could take to prevent negative consequences of someone using your general-purpose recommender system? You can propose a technical, non-technical, or policy idea. (Note that there is no “correct” answer being sought here – we are curious to see what ideas emerge from the class).

As the people who created this code, we should include warnings about the potential bias the algorithm should create. Additionally, we should ensure that the groups that have access to the code are not those that are unfriendly to minorities or women, and we must be able to trust that they will not abuse the code for malicious purposes. 