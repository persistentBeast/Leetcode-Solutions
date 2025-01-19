package com.abhishek.dsa;

import java.util.HashMap;
import java.util.Map;

public class RangeFreqQuery {

    public RangeFreqQuery(int[] arr) {
        int n = arr.length;
        root = buildTree(0, n-1, arr);
    }

    SegmentTreeNode root;

    private static class SegmentTreeNode{
        SegmentTreeNode left;
        SegmentTreeNode right;
        int l;
        int r;
        Map<Integer, Integer> occurrences;

        public SegmentTreeNode(SegmentTreeNode left, SegmentTreeNode right, int l, int r, Map<Integer, Integer> occurrences) {
            this.left = left;
            this.right = right;
            this.l = l;
            this.r = r;
            this.occurrences = occurrences;
        }
    }

    private SegmentTreeNode buildTree(int left, int right, int[] arr){
        if(left == right){
            Map<Integer, Integer> occurences = new HashMap<>();
            occurences.put(arr[left], 1);
            return new SegmentTreeNode(null, null, left, right, occurences);
        }
        int mid = (left + right)/2;
        SegmentTreeNode leftTree = buildTree(left, mid, arr);
        SegmentTreeNode rightTree = buildTree(mid + 1, right, arr);
        Map<Integer, Integer> occurrences = new HashMap<>(leftTree.occurrences);
        rightTree.occurrences.forEach((k, v) -> {
            occurrences.put(k, occurrences.getOrDefault(k, 0) + v);
        });
        return new SegmentTreeNode(leftTree, rightTree, left, right, occurrences);
    }

    public int getFreqInRange(int left, int right, int val, SegmentTreeNode node){
        if(node.l == left && node.r == right){
            return node.occurrences.getOrDefault(val, 0);
        }
        int mid = (node.l + node.r)/2;
        if(right <= mid){
            return getFreqInRange(left, right, val, node.left);
        }else if(left > mid){
            return getFreqInRange(left, right, val, node.right);
        }else{
            return getFreqInRange(left, mid, val, node.left) + getFreqInRange(mid + 1, right, val, node.right);
        }
    }

    public int query(int left, int right, int value) {
        return getFreqInRange(left, right, value, root);
    }

}
