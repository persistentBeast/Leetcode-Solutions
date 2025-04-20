package com.abhishek.dsa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BinaryTreeCodec {

     public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {

         List<String> serialisedTreeParts = new ArrayList<>();
         serializeHelper(root, serialisedTreeParts);
         StringBuilder serialisedTree = new StringBuilder();

         for (String s : serialisedTreeParts){
             serialisedTree.append(s);
             serialisedTree.append(",");
         }

         return serialisedTree.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {

         if(data == null || data.isEmpty()) return null;
         List<String> serialisedTreeParts = List.of(data.split(","));
         return deSerializeHelper(serialisedTreeParts);

    }

    public void serializeHelper(TreeNode root, List<String> serialised) {
         if(root == null) {
             serialised.add("#");
             return;
         }

         serialised.add(String.valueOf(root.val));

         serializeHelper(root.left, serialised);
         serializeHelper(root.right, serialised);
         return;
    }

    Integer index = 0;

    public TreeNode deSerializeHelper(List<String> serialised){
         if(index >= serialised.size() || serialised.get(index).equals("#")) return null;
         TreeNode node = new TreeNode(Integer.parseInt(serialised.get(index)));
         System.out.println(index);
         index = index + 1;
         TreeNode left = deSerializeHelper(serialised);
         index = index + 1;
         TreeNode right = deSerializeHelper(serialised);

         node.left = left;
         node.right = right;

         return node;
    }




}
