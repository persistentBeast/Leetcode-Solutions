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

    //26. Remove Duplicates from Sorted Array
    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        int i = 0, j = 1, k = 2;
        if(n == 1) return 1;
        while(i < n){
            if(j == n) break;
            if(nums[j] <= nums[i]){
                while(k < n && nums[k] <= nums[i]) k++;
                if(k==n){
                    break;
                }
                int temp = nums[j];
                nums[j] = nums[k];
                nums[k] = temp;
            }
            i++;
            j++;
        }
        return i+1;
    }

    //80. Remove Duplicates from Sorted Array II
    public int removeDuplicates2(int[] nums) {
        int n = nums.length;
        int i = 0, j = 1, k = 2, l = 3;
        if(n == 1 | n == 2) return n;
        while(i < n - 1){
            if(k == n) break;
            if(!removeDuplicates2Helper(nums[i], nums[j], nums[k])){
                while(l < n && !removeDuplicates2Helper(nums[i], nums[j], nums[l])) l++;
                if(l==n){
                    break;
                }
                int temp = nums[k];
                nums[k] = nums[l];
                nums[l] = temp;
            }
            i++;
            j++;
            k++;
        }
        return i+2;
    }

    private boolean removeDuplicates2Helper(int a, int b, int c){

        return (a < b && b < c) || (a <= b && b < c) | (a < b && b <= c);

    }

    
}
