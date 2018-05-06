# Network-Data-Measurement

It is crucial to know characteristics such as cardinality, size and membership status of network traffic due to several reasons. These
characteristics can form a part of Intrusion Detection Systems, help detect DDoS attack, determine which QoS should be applied etc. However, it is equally important that these measurements are done efficiently and accurately under two contraints:
i) Time Constraint: Traffic measurement should not become a bottle in the otherwise high speed network.
ii) Memory Constraint: All online computation must be performed in the main memory of the router.

This project is an implementation of some network algorithms and data structures which attempt to accomplish network data measurement under these contraints. It consist of the following algorithms:

# A) Cardinality Measurement Algorithms:

1) Double Hash Table

2) Probabilistic Counting Algorithm:
Kyu-Young Whang, Brad T. Vander-Zanden, and Howard M. Taylor. 1990. A linear-time probabilistic counting algorithm for database applications. ACM Trans. Database Syst. 15, 2 (June 1990), 208-229. DOI=http://dx.doi.org/10.1145/78922.78925

3) Virtual Bitmap:
M. Yoon, T. Li, S. Chen and J. K. Peir, "Fit a Compact Spread Estimator in Small High-Speed Memory," in IEEE/ACM Transactions on Networking, vol. 19, no. 5, pp. 1253-1264, Oct. 2011.
doi: 10.1109/TNET.2010.2080285

4) Virtual FM:
Q. Xiao et al., "Cardinality Estimation for Elephant Flows: A Compact Solution Based on Virtual Register Sharing," in IEEE/ACM Transactions on Networking, vol. 25, no. 6, pp. 3738-3752, Dec. 2017.
doi: 10.1109/TNET.2017.2753842

# B) Size Measurement Algorithm:

1) Count Min:
Graham Cormode and S. Muthukrishnan. 2005. An improved data stream summary: the count-min sketch and its applications. J. Algorithms 55, 1 (April 2005), 58-75. DOI=http://dx.doi.org/10.1016/j.jalgor.2003.12.001

# C) Membership Check Algorithm: 

1) Bloom Filter:
Broder, Andrei & Mitzenmacher, Michael. (2003). Survey: Network Applications of Bloom Filters: A Survey.. Internet Mathematics. 1. 10.1080/15427951.2004.10129096. 
