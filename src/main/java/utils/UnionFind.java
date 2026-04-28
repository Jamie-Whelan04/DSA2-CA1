package utils;
//keeps track of connected components
public class UnionFind {
    //Parent[i] points to the parent of node i
    //Size[i] stores the size of the tree rooted at i
    private int[] parent, size;

    //Constructor
    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];

        //each element is in its own set
        for (int i = 0; i < n; i++) {
            parent[i] = i; //Each node is its own parent
            size[i] = 1; //Each set has a size of 1
        }
    }

    //Finds the root of the set containing x and uses path compression to flatten the structure
    public int find(int x) {
        //If x is not the root keep recursively looking for root and compress the path by pointing x to the root
        if (x != parent[x])
            parent[x] = find(parent[x]);
        return parent[x];
    }

    //Puts the sets together containing elements a and b and then uses union by size to keep the trees shallow
    public void union(int a, int b) {
        //Finds the root of both elements
        int ra = find(a);
        int rb = find(b);

        //If thet are already in the same set, do nothing
        if (ra == rb) return;

        //Attach the smaller tree under the larger one
        if (size[ra] < size[rb]) {
            parent[ra] = rb; //Root of a points to root of b
            size[rb] += size[ra]; //Updates size of the root
        } else {
            parent[rb] = ra; //Root of b points to root of a
            size[ra] += size[rb]; //Updates size of the root
        }
    }
}