# Feature selection algorithm using ACO-Ant Colony Optimization

A novel feature selection algorithm using ACO-Ant Colony Optimization, to extract feature words from a given web page and then to generate an optimal feature set based on ACO Metaheuristics and normalized weight defined as a learning function of their learned weights, position and frequency of feature in the web page. 

To ascertain the validity of the proposed measure, we performed the experiments on web document categorization and the obtained results using the proposed measure were compared with those using other commonly used measures. 

Webkb datasets(CMU Machine Learning Repository) were adopted in our simulation experiments. Dataset along with noise was firstly fed into feature selectors, which will generate feature subsets from the datasets. After that, Newly selected features were passed to external learning algorithms to assess classification performance. NBC (Naive Bayes Classifier) and (SVM) Support Vector Machine classifier, was chosen to test prediction capability of the selected subset. All test were done on experimental platform Weka. To achieve impartial results, 10-fold cross-validations were performed on the datasets using both the classifier.

- Developed a novel Feature Selection algorithm based on Ant Colony Optimization (ACO) Metaheuristics. Web page classification task was performed to ascertain the validity of the proposed method on WEBKB dataset. Achieved significant improvement in the classification performance of Naive Bayes classifier (NBC) and Support Vector Machine (SVM) classifiers. Completed the thesis under the guidance of Dr. Shine N Das.
- Experimental results: using NBC- 0.853, 0.788, 0.814, 0.937 using SVM 0.760, 0.873, 0.807, 0.936 (in the following order IR-precision, IR-Recall, F-Measure, Area under precision-recall curve (AUC)).

