package com.abhishek.dsa;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DSU {

    int[] parent;
    int totalComponents;

    public DSU(int n) {
        this.parent = new int[n];
        for(int i = 0; i < n; i++) parent[i] = i;
    }

    public void addEdge(int a, int b){
        int parentA = parent[a];
        int parentB = parent[b];

        while(parentB  != parentA){
            int temp = parentB;
            parent[b] = parentA;
            parentB = parent[temp];
        }

    }

    public int getTotalComponents(){
        Set<Integer> nodes = new HashSet<>();
        for (int i = 0; i < parent.length; i++) {
            nodes.add(parent[i]);
        }
        return nodes.size();
    }

}
