package com.abhishek.dsa;

public class RangeQuery {

    SegmentTreeNode root;

    private static class SegmentTreeNode{
        SegmentTreeNode left;
        SegmentTreeNode right;
        int val;
        int l;
        int r;

        public SegmentTreeNode(SegmentTreeNode left, SegmentTreeNode right, int val, int l, int r) {
            this.left = left;
            this.right = right;
            this.val = val;
            this.l = l;
            this.r = r;
        }

    }

    private SegmentTreeNode buildTree(int left, int right, int[] nums){
        if(left == right){
            return new SegmentTreeNode(null, null, nums[left], left, right);
        }
        int mid = (left + right)/2;
        SegmentTreeNode leftTree = buildTree(left, mid, nums);
        SegmentTreeNode rightTree = buildTree(mid + 1, right, nums);
        return new SegmentTreeNode(leftTree, rightTree, leftTree.val + rightTree.val, left, right);
    }

    public void updateTree(int index, int val, SegmentTreeNode node){
        if(node.l == index && node.r == index){
            node.val = val;
            return;
        }
        int mid = (node.l + node.r)/2;
        if(index <= mid){
            updateTree(index, val, node.left);
        }else{
            updateTree(index, val, node.right);
        }
        node.val = (node.left.val) + node.right.val;
    }

    public int getRangeVal(int left, int right, SegmentTreeNode node){
        if(node.l == left && node.r == right){
            return node.val;
        }
        int mid = (node.l + node.r)/2;
        if(right <= mid){
            return getRangeVal(left, right, node.left);
        }else if(left > mid){
            return getRangeVal(left, right, node.right);
        }else{
            return getRangeVal(left, mid, node.left) + getRangeVal(mid + 1, right, node.right);
        }
    }

    public RangeQuery(int[] nums) {
        int n = nums.length;
        root = buildTree(0, n - 1, nums);
    }

    public void update(int index, int val) {
        updateTree(index, val, root);
    }

    public int sumRange(int left, int right) {
        return getRangeVal(left, right, root);
    }
}
