package main.java.com.abhishek.dsa;
public class Solution {
    

    //27. Remove Element
    public int removeElement(int[] nums, int val) {
        int n  = nums.length;
        int i = 0, j = n - 1;
        while(i <= j){
            if( nums[i] == val){
                while(nums[j]== val && j > i) j--;
                if(j > i) {
                    nums[i] = nums[j];
                    nums[j] = val;
                    i++;
                }else{
                    break;
                }
            }else{
                i++;
            }
        }
        return i;
    }

}
