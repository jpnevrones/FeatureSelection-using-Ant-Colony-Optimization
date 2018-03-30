## Table of Contents
{:.no_toc}
0. this unordered seed list will be replaced by toc as unordered list
{:toc}

##Summary:

A novel **feature selection algorithm using ACO-Ant Colony Optimization**, to extract feature words from a given web page and then to generate an optimal feature set based on ACO Metaheuristics and normalized weight defined as a learning function of their learned weights, position and frequency of feature in the web page. 

To ascertain the validity of the proposed measure, we performed the experiments on web document categorization and the obtained results using the proposed measure were compared with those using other commonly used measures. 

**Webkb datasets**(CMU Machine Learning Repository) were adopted in our simulation experiments. Dataset along with noise was firstly fed into feature selectors, which will generate feature subsets from the datasets. After that, Newly selected features were passed to external learning algorithms to assess classification performance. **NBC (Naive Bayes Classifier)** and **(SVM) Support Vector Machine** classifier, was chosen to test prediction capability of the selected subset. All test were done on experimental platform Weka. To achieve impartial results, 10-fold cross-validations were performed on the datasets using both the classifier.

- Developed a novel **Feature Selection algorithm based on Ant Colony Optimization (ACO) Metaheuristics**. Web page classification task was performed to ascertain the validity of the proposed method on WEBKB dataset. Achieved significant improvement in the classification performance of **Naive Bayes classifier (NBC)** and **Support Vector Machine (SVM)** classifiers. Completed the thesis under the guidance of **Dr. Shine N Das**.
- **Experimental results:** using NBC- **0.853, 0.788, 0.814, 0.937** using SVM **0.760, 0.873, 0.807, 0.936** (in the following order IR-precision, IR-Recall, F-Measure, Area under precision-recall curve (AUC)).
- Was awarded as the **Best undergraduate thesis project** by the university.

## Abstract

Web Feature selection is the essential steps in web page classification systems. Web Feature selection is commonly used to reduce the dimensionality of datasets with tens or hundreds of thousands of features which would be impossible to process further.A significant problem of web featuring is the high dimensionality of the feature space; therefore, Web feature selection is the most critical step in web featuring, but only reducing the dimensionality of feature space will not lead to efficient web page categorization.

Henceforth we present a novel, Web feature selection algorithm that is based on Ant Colony Optimization, which optimizes the extracted feature from the web pages using the population-based metaheuristics. Ant Colony Optimization algorithm is inspired by the behavior of real ants, that comprises of a parallel search over several constructive computational threads based on local problem data and on a dynamic memory structure containing information on the quality of the previously obtained result. The collective behavior merging from the interaction of the different search threads has proved useful in solving combinatorial optimization(CO) problems. To apply Ant Colony Optimization,the combinatorial optimization problem is transformed into the problem of finding the best path on a weighted [graph](http://www.scholarpedia.org/article/Graph_Theory).The artificial ants (software mutant of real ants) incrementally build solutions by moving on the graph. The solution construction process is stochastic and is biased by a *pheromone model*, that is, a set of parameters associated with graph components (either nodes or edges) whose values are modified at runtime by the ants. The proposed algorithm is easily implemented, and because of use of a simple classifier in that, its computational complexity is very low.



## 1 Introduction

### 1.1 Web Feature Selection

*Web Feature selection* is the process of selecting a subset of the terms occurring in the training set and using only this subset as features in webpage classification. Feature selection serves two primary purposes. First, it makes training and applying a classifier more efficient by decreasing the size of the rich vocabulary. This is of particular importance for classifiers that, unlike NB,are expensive to train. Second, feature selection often increases classification accuracy by eliminating noise features. A *noise feature* is one that,when added to the document representation, increases the classification error on new data. Suppose a rare term, say technocentric, has no information about a class, say China, but all instances of technocentric happen to occur in China documents in our training set. Then the learning method might produce a classifier that miss-assigns test documents are containing arachnocentric to China. Suchan incorrect generalization from an accidental property of the training set is called *overfitting* . 


Several feature selection measures have been explored in the text filtering literature including Document Frequency (DF), Information Gain (IG), Mutual Information (MI), Chi-square (CHI), Correlation Coefficient (CC),Odds Ratio (OR) and GSS Coefficient (GSS) [1, 2, 3, 4, 5, 7]. Out of the seven features, CHI, CC, OR and GSS seem to be the most effective based on the experiments reported so far. We will focus on the four measures in this study.This paper presents a feature selection framework, in which the terms highly indicative of membership and non membership are selected separately, and then combined explicitly afterward. Several standard methods are unified by

this framework, and a new method is proposed that combines the terms most indicative of membership and non membership for each category in a way such that the optimal performance, e.g., F1 measure, will be obtained on a validation set. The features indicative of membership and non-membership are also referred to as the positive and negative features respectively.

### 1.2 Feature selection measures

##### 1.2.1 Chi-square (CHI) 

Chi-square measures the lack of independence between a term *t* and a category *ci* andcan be compared to the chi-square distribution with one degree of freedom to judge extremeness [7, 5]. 

It is defined as:


##### 1.2.2Correlation coefficient (CC) 

Correlation coefficient of a word *t* with a category *ci *was defined by Ng etal. as [3, 5]

It is a variant of the CHI metric, where . CC can be viewed as a“one-sided” chi-square metric. The positive values correspond to features indicative of membership, while negative values indicate non membership.Feature selection using CC selects the terms with maximum CC values. The rationale behind is that terms coming from the non-relevant texts of a category are considered useless. On the other hand, CHI is non-negative, whose value indicates either membership or non-membership of a term to one category.Accordingly the ambiguous features will be ranked lower. In contrast with CC,CHI considers the terms coming from both the relevant and non-relevant texts.


##### 1.2.3 Odds ratio (OR) 

Odds ratio was proposed initially by Van Rijsbergen etal. for selecting terms for relevance feedback [4]. The basic idea is that the distribution of features on the relevant documents is different from the distribution of features on the non-relevant documents. It has been used by Mladenic for selecting terms in text categorization [2]. It is defined as follows:



##### **1.2.4 GSS coefficient (GSS) **

GSS coefficient is another simplified variant of the statistics  proposed by Galavotti et al. [1],which is defined as:   Similar to CC, OR and GSS only consider the positive features.



##### **1.2.5 OR-square (ORS) and GSS-square (GSSS) **

In the spirit of the relationship between CHI and CC, we propose two variants of OR and GSS: OR-square (ORS) and GSS square (GSSS) respectively as follows:



Similar to CHI, ORS or GSSS isnon-negative, whose value represents the strength of a term indicatingmembership or non-membership, e.g.,non-ambiguity. CC, OR and GSS can be considered as “one-sided” CHI, ORS andGSSS respectively. In summary, local feature selection using CC, OR and GSSpick out the terms most indicative of membership. They will never considernegative features unless all the positive features have already been selected.On the other hand, local feature selection using CHI, ORS, and GSSS do not differentiate between the termsindicative of membership

and non-membership by comparingtheir squared CC, OR and GSS values respectively (e.g., CHI, ORS and GSSS values respectively). Among those termswith positive-negative CC (OR or GSS)values, the greater/smaller more significantis, the more likely it will be selected by the standard method using CHI (ORSor GSSS respectively). Therefore, they implicitly combine the terms mostindicative

of membership and non-membership.The size ratio between the positive and negative features is internally and implicitly decided by threshold on the number of features with the highest squared values, e.g., the size of the feature set.



## 2.  Ant Colony Optimization

**Ant colony optimization** (ACO) is a population-basedmetaheuristic that can be used to find approximate solutions to difficult optimizationproblems.

InACO, a set of software agents called *artificial ants* search for right solutions to a given optimizationproblem. To apply ACO, the optimizationproblem is transformed into the problem of finding the best path on a weighted [graph](http://www.scholarpedia.org/article/Graph_Theory).The artificial ants (hereafter ants) incrementally build solutions by moving onthe graph. The solution construction process is stochastic and is biased by a *pheromonemodel*, that is, a set of parameters associated with graph components(either nodes or edges) whose values are modified at runtime by the ants.** **

Theeasiest way to understand how ant colony optimization works are by means of an example. We consider itsapplication to the traveling salesman problem (TSP). In the TSP a set oflocations (e.g., cities) and thedistances between them are given. The problem consists of finding a final tour of minimal length that visits eachcity once and only once.

Toapply ACO to the TSP, we consider the graph defined by associating the set ofcities with the set of vertices of the graph. This graph is called *constructiongraph*. Since in the TSP it is possible to move from any given city to anyother city, the construction graph is fully connected, and the number of vertices is equal to the number of cities. We setthe lengths of the edges between the vertices to be proportional to thedistances between the cities represented by these vertices and us associate pheromone values and heuristicvalues with the edges of the graph. Pheromone values are modified at runtimeand represent the cumulated experience of the ant colony, while heuristicvalues are problem dependent values that, in the case of the TSP, are set to bethe inverse of the lengths of the edges.

Theants construct the solutions as follows. Each antstarts from a randomly selected city (vertex of the construction graph). Then,at each construction step, it moves alongthe edges of the graph. Each and keeps a [memory](http://www.scholarpedia.org/article/Memory) ofits path, and in subsequent steps, itchooses among the edges that do not lead to vertices that it has alreadyvisited. An ant has constructed a solution once it has visited all the verticesof the graph. At each construction step, an ant probabilistically chooses theedge to follow among those that lead to yet unvisited vertices. Theprobabilistic rule is biased by pheromone values and heuristic information: thehigher the pheromone and the heuristic value associated with an edge, the higher the probability an ant will choose thatparticular edge. Once all the ants have completed their tour, the pheromone onthe edges is updated. Each of the pheromone values is initially decreased by acertain percentage. Each edge then receives an amount of additional pheromoneproportional to the quality of the solutions to which it belongs (there is onesolution per ant).

Thisprocedure is repeatedly applied until a termination criterion is satisfied

![overview](/img/Aco/ant.png)

The idea comes from observing the exploitation of food resources among ants, in which ants’ individually limited cognitive abilities have collectively been able to find the shortest path between a food source and the nest.

1. The first ant finds the food source (F), via anyway (a), then returns to the nest (N),     leaving behind a trail pheromone (b)
2. Ants indiscriminately     follow four possible ways, but the strengthening of the runway makes it     more attractive as the shortest route.
3. Ants take the shortest route;     long portions of other ways lose     their trail pheromones.

In a series of experiments on a colony of ants with a choice between two unequal length paths leading to a source of food, biologists have observed that ants tended to use the shortest route. A model explaining this behaviorism as follows:

1. An ant (called     "blitz") runs more or less at random around the colony;
2. If it discovers a food     source, it returns more or less directly to the nest, leaving in its path     a trail of pheromone;
3. These pheromones are     attractive; nearby ants will be     inclined to follow, more or less directly, the track;
4. Returning to the colony,     these ants will strengthen the route;
5. If there are two routes     to reach the same food source then, in a given amount of time, the shorter     one will be traveled by more ants     than the long route;
6. The short route will be     increasingly enhanced, and therefore become more attractive;
7. The long route will     eventually disappear because pheromones are volatile;
8. Eventually, all the ants     have determined and therefore "chosen" the shortest route.

### **2.1.      ACO Meta-Heuristic**

In ACO, artificial ants build a solution to a combinatorialoptimization problem by traversing a fully connected construction graph,defined as follows. First, each instantiated decision variable Xi=vij is called a *solution component* anddenoted by cij .The set of all possible solution components is denoted by C. Then the construction graph *GC*(*V,E*)is defined by associating the components *C* either with the set ofvertices *V* or with the set of edges *E*.

Apheromone trail value τij is associated witheach component cij.(Note that pheromone values are in general a function of the [algorithm](http://www.scholarpedia.org/article/Algorithm)'siteration t: τij=τij(t) .) Pheromonevalues allow the probability distribution of different components of thesolution to be modeled. Pheromone values are used and updated by the ACOalgorithm during the search.

Theants move from vertex to vertex along the edges of the construction graphexploiting the information provided bythe pheromone values and in this way incrementally building a solution.Additionally, the ants deposit a certain amount of pheromone on the components,that is, either on the vertices or on the edges that they traverse. The amount Δτ of pheromone deposited may depend on the qualityof the solution found. Subsequent ants utilize the pheromone information as aguide towards more promising regions of the search space.

The ACO Meta heuristic is:

```
Set parameters, initialize pheromone trails
SCHEDULE_ACTIVITIES
  ConstructAntSolutions
  DaemonActions    {optional}
  UpdatePheromones
END_SCHEDULE_ACTIVITIES
```

Themetaheuristic consists of aninitialization step and of three algorithmic components whose activation isregulated by the `SCHEDULE_ACTIVITIES`construct. This construct is repeated until a termination criterion is met.Typical criteria are a maximum number of iterations or a maximum CPU time.

The`SCHEDULE_ACTIVITIES`construct does not specify how the three algorithmic components are scheduledand [synchronized](http://www.scholarpedia.org/article/Synchronization). In most applications of ACO to NP-hardproblems however, the three algorithmic components undergo a loop that consistsin (i) the construction of solutions by all ants, (ii) the (optional)improvement of these solution via the use of a local search algorithm, and(iii) the update of the pheromones. These three components are now explained inmore details.

#### 2.1.1 Construct Ant Solutions

Aset of m artificial ants construct solutionsfrom elements of a finite set of available solution components C={cij} , i=1,…,n ,j=1,…,|Di| . A solution constructionstarts with an empty partial solution sp=∅ .Then, at each construction step, the current partial solution sp is extended by adding a feasible solutioncomponent from the set of feasible neighbors N(sp)⊆C .The process of constructing solutions can be regarded as a path on theconstruction graph *GC*(*V,E*). The allowed paths in *GC*are implicitly defined by the solution construction mechanism that defines theset N(sp) with respect to a partial solution sp*.*

Thechoice of a solution component from N(sp) is done probabilistically at each constructionstep. The exact rules for the probabilistic choice of solution components varyacross different ACO variants. The best-knownrule is the one of ant system (AS) (Dorigo et al. 1991, 1996):

 

Where τij and ηij are respectively the pheromone value and theheuristic value associated with the component cij .Furthermore, α and βare positive real parameters whose valuesdetermine the relative importance of pheromone versus heuristic information.

#### 2.1.2 Daemon Actions

Oncesolutions have been constructed, and before updating the pheromone values,often some problem specific actions may be required. These are often called *daemonactions* and can be used to implementproblem specific and/or centralized actions, which cannot be performed bysingle ants. The most used daemon action consists in the application of localsearch to the constructed solutions: the locally optimized solutions are thenused to decide which pheromone values to update.

#### 2.1.3 Update Pheromones

Theaim of the pheromone update is to increase the pheromone values associated withgood solutions and to decrease those thatare associated with bad ones. Usually, this is achieved (i) by decreasing allthe pheromone values through *pheromone evaporation*, and (ii) byincreasing the pheromone levels associated with a chosen set of good solutions Supd:

 

whereSupd is the set of solutions that are used forthe update, ρ∈(0,1] is a parametercalled evaporation rate, and F:S→R0+ is afunction such that

 

F(⋅) is commonly called the *fitness function*.

Pheromoneevaporation implements a useful form of *forgetting*, favoring theexploration of new areas in the search space. Different ACO algorithms, for example, ant colony system (ACS) (Dorigo &Gambardella 1997) or MAX-MIN ant system (MMAS) (Stützle & Hoos 2000),differ in the way they update the pheromone.

Instantiationsof the update rule given above are obtained by different specifications of Supd , which in many cases is a subset of Siter∪{sbs} , where Siter is the set of solutions that were constructedin the current iteration, and SBS is the *best-so-far* solution, that is, the bestsolution found since the first algorithm iteration. A well-known example is an AS-update rule, that is, the update rule ofant system (Dorigo et al. 1991, 1996): Supd←Siter .

Anexample of a pheromone update rule that is more often used in practice is theIB-update rule (where IB stands for *iteration-best*):

 

TheIB-update rule introduces a much stronger bias towards the good solutions foundthan the AS-update rule. Although this increases the speed with which goodsolutions are found, it also increases the probability of prematureconvergence. An even stronger bias is introduced by the BS-update rule, whereBS refers to the use of the best-so-far solution SBS . In this case, Supd is set to {ssb} .In practice, ACO algorithms that use variations of the IB-update or theBS-update rules and that additionally include mechanisms to avoid prematureconvergence, achieve better results than those that use the AS-update rule.



### 2.2        Main ACO Algorithms

Severalspecial cases of the ACO metaheuristichave been proposed in the literature. Here we briefly overview, in thehistorical order in which they were introduced, the three most successful ones:ant system (Dorigo 1992, Dorigo et al. 1991, 1996), ant colony system (ACS)(Dorigo & Gambardella 1997), and MAX-MIN ant system (MMAS) (Stützle &Hoos 2000). In order to illustrate the differences between them clearly, we usethe example of the traveling salesman problem.

#### 2.2.1 Ant system

Antsystem (AS) was the first ACO algorithm to be proposed in the literature(Dorigo et al. 1991, Dorigo 1992, Dorigo et al. 1996). Its main characteristicis that the pheromone values are updated by *all* the ants that havecompleted the tour. Solution components cij are the edges of the graph, and thepheromone update for τij , that is, forthe pheromone associated to the edge joining cities iand j , is performed as follows:

   

whereρ∈(0,1] is theevaporation rate, m is the number of ants, andΔτijk is the quantity of pheromone laid onedge (i,j) by the k-thant:

 

where Lk is the tourlength of the k-th ant.

Whenconstructing the solutions, the ants in AS traverse the construction graph andmake a probabilistic decision at each vertex. The transition probability p(cij| ) of the k-th ant moving from city ito city j is given by:

 

whereN( ) is the set ofcomponents that do not belong yet to the partial solution skp of ant k ,and α and βare parameters that control the relative importance of the pheromone versus theheuristic information ηij=1/dij , where dij is the length of component cij (i.e., of edge (i,j)).

#### 2.2.2 Ant colony system

Thefirst major improvement over the originalant system to be proposed was ant colony system (ACS), introduced by Dorigo andGambardella (1997). The first important differencebetween ACS and AS is the form of the decision rule used by the ants during theconstruction process. Ants in ACS use the so-called *pseudorandomproportional* rule: the probability for an ant to move from city I to city j depends on a random variable q uniformly distributed over [0,1] , and a parameter q0 ; if q≤q0 ,then, among the feasible components, the component that maximizes the product τilηilβ is chosen; otherwise, the same equation asin AS is used.

Thisrather greedy rule, which favors exploitation of the pheromone information, iscounterbalanced by the introduction of a diversifying component: the *localpheromone update*. The local pheromone update is performed by all ants aftereach construction step. Each ant appliesit only to the last edge traversed:

 

Where  φ∈(0,1] is the pheromone decay coefficient,and τ0 is the initial value of the pheromone.

Themain goal of the local update is todiversify the search performed by subsequent ants during one iteration. Infact, decreasing the pheromone concentration on the edges as they are traversedduring one iteration encourages subsequent ants to choose other edges and henceto produce different solutions. This makes less likely that several antsproduce identical solutions during oneiteration. Additionally, because of the local pheromone update in ACS, theminimum values of the pheromone are limited.

Asin AS, also in ACS at the end of the construction process a pheromone update,called *offline* pheromone update, is performed.

ACSoffline pheromone update is performed only by the best ant, that is, only edgesthat were visited by the best ant areupdated, according to the equation:

 

where =1/Lbest if thebest ant used edge (i,j) in its tour,  =0 otherwise (Lbest can be set to either the length of the besttour found in the current iteration -- *iteration-best*, Lib -- or the best solution found since the start ofthe algorithm -- *best-so-far*, Lbs).

Itshould be noted that most of the innovations introduced by ACS were introducedfirst in Ant-Q, a preliminary version of ACS by the same authors.

#### 2.2.3 MAX-MIN ant system

MAX-MINant system (MMAS) is another improvement, proposed by Stützle and Hoos (2000),over the original ant system idea. MMAS differs from AS in that (i) only thebest ant adds pheromone trails, and (ii) the minimum and maximum values of thepheromone are explicitly limited (in AS and ACS these values are limitedimplicitly, that is, the value of the limits is a result of the algorithmworking rather than a value set explicitly by the algorithm designer).

Thepheromone update equation takes the following form:

 

where =1/Lbest if thebest ant used edge (i,j) in its tour,  =0 otherwise, whereLbest is the length of the tour of the bestant. As in ACS, Lbest may be set (subject tothe algorithm designer decision) either to Libor to Lbs , or to a combination of both.

Thepheromone values are constrained between τminand τmax by verifyingafter they have been updated by the ants, that all pheromone values are withinthe imposed limits: τij is set to τmax if τij>τmaxand to τmin,if τij<τmin . It is important to notethat the pheromone update equation of MMAS is applied, as it is the case forAS, to all the edges while in ACS it is applied only to the edges visited bythe best ants.

Theminimum value τmin is most often experimentallychosen (however, some theory about how to define its value analytically hasbeen developed in (Stützle & Hoos 2000)). The maximum value τmax may be calculated analytically provided thatthe optimum ant tour length is known. In the case of the TSP, τmax=1/(ρ⋅L∗) ,where L∗ is the length of theoptimal tour. If L∗ is not known, it canbe approximated by Lbs . It is alsoimportant to note that the initial value of the trails is set to τmax and thatthe algorithm is restarted when no improvement can be observed for a givennumber of iterations.

  

## 3 Overview of proposed system

We present a novel web feature selection algorithm, which isintended for producing an optimal featureset, using Ant Colony Optimization Algorithm for efficient classification ofweb pages. The proposed system can divide intopart feature extraction process and optimal feature selection mechanism using AntColony Optimization.

Feature are extracted from the web pages using featureextraction process, in order get the feature set, now this selected feature isoptimized using ACO. In ACO, a set of software agents called *artificial ants*search for good solutions to a givenoptimization problem. To apply ACO, theoptimization problem is transformed into the problem of finding the best pathon a weighted [graph](http://www.scholarpedia.org/article/Graph_Theory). The artificial ants (hereafter ants)incrementally build solutions by moving on the graph. The solution constructionprocess is stochastic and is biased by a *pheromone model*, that is, a setof parameters associated with graph components (either nodes or edges) whosevalues are modified at runtime by the ants.** **A graph is generated with a feature from the feature set as nodes alongsidetheir frequency. Now ACO is applied to the graph, each iteration of ACO willgenerate the best path or best featuresubset solution. The best feature subsetsolution is compared with the previously obtained results if they are not thesame then the current path is saved ,if it is more optimal, then previous one andthe pheromone is added to this path. Otherwise, if the solution starts converging,then we get our optimal feature subset, which is later used for effective categorization of web pages 

![overview](/img/Aco/OVERVIEW WACO.jpg)

### 3.1 ACO based Algorithm for Feature selection 

**Input:** Web pages to feature extraction process.

**Output:** Optimal feature subset for web page categorization

**Algorithm:**

1.    Feature extraction from the web pages, to generate Feature set.

2.    Createa graph G, with a featurein feature set as node and frequency of feature obtained in Feature extractionprocess there weights.

      $G(F_n,F)$, Where $F_n$, is feature nodes which belong to Feature set and $F$ is the frequency of $F_n$ as obtained during Feature extraction process.

3.    Apply ACO on the graph $G(F_n,F)$

  3.1  Generate the Artificial Ants.

  3.2  Initialize the Pheromone

  3.3   **For** Ants = 1 to m

  	3.3.1       Evaluateeach ant, If more features to choose then continue further Else break the loop.
  	3.3.2       Applythe transition rule to select the new feature to move on.
    3.4  Gather all the selected Feature subset.

    3.5  Evaluate the selected feature subset.
    3.6  Check whether the best feature subset selected inthe previous step converges with theearlier obtained one
  	3.6.1       If truethen stop the algorithm and move to step 
  	3.6.2       Otherwise, if the current solution is optimal compared topreviously obtained one then, update the  pheromone value.
  	3.6.3       AllAnts up to now are killed, and new Ants are                           generated a moveto step 3.3 and repeat the entire computation.

4.    Output out the optimal feature set for efficientweb page categorization.  


![Flow chart](/img/Aco/FLOWCHART.jpg)


## 4 Conclusion

We presented a novel technique which in corporates web page extraction and optimization of the extracted feature set using Ant Colony Optimization algorithm, to create an efficient web page categorization process.

Ant colony Optimization process has been proved more efficient compared to the Genetic algorithm ,Chi-Square Statistics and Information Gain methods, as after the extraction process the web page will merely act as text feature selection with ACO will lead to same excellent results of text feature  selection using ACO.

The current proposed system leads to the creation of an optimal feature set for web page categorization. As future work, we would like to incorporate Dynamic Mutual Information along with ACO hence creating a logical and optimal feature set for categorization of web pages which in turn help in improving the web page categorization efficiency.

Incorporating Dynamic Mutual Information along with ACO will result in a different variant of current ACO algorithm called Multi objective ACO. In Multi objective ACO, we apply heuristics on the problem with multiple objectives, i.e., in order to obtain a solution while applying a heuristic,multiple objectives are concerned.  

