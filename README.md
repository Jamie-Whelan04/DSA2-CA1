# Leaf Identification System

**DSA2 – CA1** | Jamie Whelan



---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Data Structures & Algorithms Used](#data-structures--algorithms-used)
- [Author](#author)

---

## Overview

This project was built for Data Structures & Algorithms 2 (continuous assessment 1). 

JavaFX-based application that uses image
processing and a union-find algorithm to locate,
count, and size-order autumn leaves from an input
photo. Featuring an animated TSP route connecting
all leaf clusters

## Features

- *Can identify leaf clusters*
- *Seperates into blocks*
- *Assigns a value based on the cluster size*

## Project Structure

```
DSA2-CA1/
├── src/
   ├── Main
      ├── java
         ├── benchmarks
            ├── leafBenchmark.java
         ├── controllers
            ├── mainController.java
         ├── org/example
            ├── mainApp.java
         ├── utils
            ├── imageProcessor.java
            ├── leafCluster.java
            ├── leafRenderer.java
            ├── mergeSort.java
            ├── TspSolver.java
            ├── UnionFind.java
         ├── resources/org/example
            ├── primary.fxml
   ├── test/java/utils
      ├── LeafClusterTest.java
      ├── MergeSortTest.java
      ├── TSPSolverTest.java
      ├── UnionFindTest.java             
├── pom.xml            # Maven build configuration
├── .gitignore
└── README.md
```


## Getting Started

### Prerequisites

- Java JDK [version, e.g. 17 or later]
- Maven (or an IDE with Maven support, such as IntelliJ IDEA)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Jamie-Whelan04/DSA2-CA1.git
   cd DSA2-CA1
   ```
2. Build the project:
   ```bash
   mvn clean install
   ```

## Usage

<img width="1495" height="1083" alt="image" src="https://github.com/user-attachments/assets/739e1929-af25-4122-a797-98ec35d463d5" />
<img width="1497" height="1087" alt="image" src="https://github.com/user-attachments/assets/e3c5761d-6fc4-49ef-bf3a-ec0aa13fb10a" />
<img width="1498" height="1089" alt="image" src="https://github.com/user-attachments/assets/355dc18e-8b4a-4367-979c-92c998c1a845" />


## Data Structures & Algorithms Used

| Component | Data Structure / Algorithm | Reason |
|-----------|---------------------------|--------|
| *(e.g. storing data)* | *(e.g. tree / graph / hash map)* | *(why it was chosen)* |
| *(e.g. searching / traversal)* | *(e.g. BFS / DFS / binary search)* | *(why it was chosen)* |
| *(e.g. sorting)* | *(e.g. merge sort / heap sort)* | *(why it was chosen)* |

## Author

**Jamie Whelan** – [@Jamie-Whelan04](https://github.com/Jamie-Whelan04)

---

*Created for DSA2 – CA1.*
