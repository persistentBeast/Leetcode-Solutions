package com.abhishek.dsa;

import java.util.*;

public class Solution {


    //27. Remove Element
    public int removeElement(int[] nums, int val) {
        int n = nums.length;
        int i = 0, j = n - 1;
        while (i <= j) {
            if (nums[i] == val) {
                while (nums[j] == val && j > i) j--;
                if (j > i) {
                    nums[i] = nums[j];
                    nums[j] = val;
                    i++;
                } else {
                    break;
                }
            } else {
                i++;
            }
        }
        return i;
    }

    //26. Remove Duplicates from Sorted Array
    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        int i = 0, j = 1, k = 2;
        if (n == 1) return 1;
        while (i < n) {
            if (j == n) break;
            if (nums[j] <= nums[i]) {
                while (k < n && nums[k] <= nums[i]) k++;
                if (k == n) {
                    break;
                }
                int temp = nums[j];
                nums[j] = nums[k];
                nums[k] = temp;
            }
            i++;
            j++;
        }
        return i + 1;
    }

    //80. Remove Duplicates from Sorted Array II
    public int removeDuplicates2(int[] nums) {
        int n = nums.length;
        int i = 0, j = 1, k = 2, l = 3;
        if (n == 1 | n == 2) return n;
        while (i < n - 1) {
            if (k == n) break;
            if (!removeDuplicates2Helper(nums[i], nums[j], nums[k])) {
                while (l < n && !removeDuplicates2Helper(nums[i], nums[j], nums[l])) l++;
                if (l == n) {
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
        return i + 2;
    }

    private boolean removeDuplicates2Helper(int a, int b, int c) {

        return (a < b && b < c) || (a <= b && b < c) | (a < b && b <= c);

    }

    //121. Best Time to Buy and Sell Stock
    public int maxProfit(int[] prices) {

        int n = prices.length;
        int min_stock = 1000000;
        int max_profit = 0;

        for (int i = 0; i < n; i++) {
            if (prices[i] < min_stock) {
                min_stock = prices[i];
            } else {
                max_profit = Math.max(max_profit, prices[i] - min_stock);
            }

        }

        return max_profit;

    }

    //122. Best Time to Buy and Sell Stock II
    public int maxProfit2(int[] nums) {

        int n = nums.length;
        int ans = 0, st = 0;

        for (int i = 1; i < n; i++) {

            if (nums[i] < nums[i - 1]) {
                ans += nums[i - 1] - nums[st];
                st = i;
            }

        }

        ans += nums[n - 1] - nums[st];

        return ans;

    }

    //3075. Maximize Happiness of Selected Children
    public long maximumHappinessSum(int[] happiness, int k) {
        int n = happiness.length, pen = 0;
        long ans = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 0; i < n; i++) {
            pq.add(happiness[i]);
        }
        for (int i = 0; i < k; i++) {
            Integer tp = pq.poll();
            tp -= pen;
            tp = Math.max(0, tp);
            ans += tp;
            pen++;
        }
        return ans;
    }

    //3075. Maximize Happiness of Selected Children
    public long maximumHappinessSum2(int[] happiness, int k) {
        int n = happiness.length, pen = 0;
        long ans = 0;
        Arrays.sort(happiness);
        for (int i = n - 1; i > (n - 1 - k); i--) {
            Integer tp = happiness[i];
            tp -= pen;
            tp = Math.max(0, tp);
            ans += tp;
            pen++;
        }
        return ans;
    }


    public static class Pair {
        public int l;
        public int r;

        public Pair(int l, int r) {
            this.l = l;
            this.r = r;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "l=" + l +
                    ", r=" + r +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return l == pair.l && r == pair.r;
        }


        @Override
        public int hashCode() {
            return Objects.hash(l, r);
        }


    }

    static class PairComparator implements Comparator<Pair> {
        public int compare(Pair p1, Pair p2) {
            // Compare based on the first element of the pair
            if (p1.l != p2.l) {
                return Integer.compare(p1.l, p2.l);
            }
            // If first elements are equal, compare based on the second element
            return Integer.compare(p1.r, p2.r);
        }
    }

    //220. Contains Duplicate III
    public boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff) {
        int n = nums.length;
        TreeSet<Pair> set = new TreeSet<>(new PairComparator());
        int st = 0, end = 0;
        for (int i = 0; i < n; i++) {
            while (end < n && end <= i + indexDiff) {
                set.add(new Pair(nums[end], end));
                end++;
            }

            while (st < i - indexDiff) {
                set.remove(new Pair(nums[st], st));
                st++;
            }
            set.remove(new Pair(nums[i], i));

            Pair h = set.floor(new Pair(nums[i] + valueDiff, i));
            Pair l = set.ceiling(new Pair(nums[i] - valueDiff, i));

            if (h != null && h.r != i && Math.abs(h.l - nums[i]) <= valueDiff) return true;
            if (l != null && l.r != i && Math.abs(l.l - nums[i]) <= valueDiff) return true;

            set.add(new Pair(nums[i], i));

        }

        return false;
    }


    public long maximumSubarraySum(int[] nums, int k) {

        int n = nums.length;

        Map<Integer, Integer> mp = new HashMap<>();
        long[] pSum = new long[n];

        boolean anyGoodArr = false;
        long ans = Long.MIN_VALUE;

        for (int i = 0; i < n; i++) {

            if (i == 0) {
                pSum[i] = nums[i];
            } else {
                pSum[i] = pSum[i - 1] + nums[i];
            }

            if (mp.containsKey(nums[i])) {
                if (pSum[i] - pSum[mp.get(nums[i])] + nums[i] > 0) {
                    mp.put(nums[i], i);
                }
            } else {
                mp.put(nums[i], i);
            }

            int k1 = nums[i] + k, k2 = nums[i] - k;
            if (mp.containsKey(k1)) {
                anyGoodArr = true;
                ans = Math.max(ans, pSum[i] - pSum[mp.get(k1)] + k1);
            }
            if (mp.containsKey(k2)) {
                anyGoodArr = true;
                ans = Math.max(ans, pSum[i] - pSum[mp.get(k2)] + k2);
            }
        }
        return anyGoodArr != true ? 0 : ans;

    }

    //983. Minimum Cost For Tickets
    public int mincostTickets(int[] days, int[] costs) {
        int n = days.length;
        int[] dp = new int[366];
        int j = 0;

        for (int i = 0; i < 366; i++) {
            if (j < n && i < days[j]) {
                dp[i] = (i - 1 < 0) ? 0 : dp[i - 1];
            } else {
                if (j < n) {
                    int c1 = costs[0] + dp[days[j] - 1];
                    int c2 = costs[1] + (days[j] - 7 < 0 ? 0 : dp[days[j] - 7]);
                    int c3 = costs[2] + (days[j] - 30 < 0 ? 0 : dp[days[j] - 30]);
                    dp[i] = Math.min(c1, Math.min(c2, c3));
                    j++;
                }
            }
        }
        return dp[days[n - 1]];
    }

    int mod = 1000000007;

    public long checkRecordHelper(int n, int contCount, int absentCount, Map<String, Long> dp) {
        if (n == 1) {
            if (absentCount == 1 && contCount == 2) return 1;
            else if (absentCount == 1 || contCount == 2) return 2;
            else return 3;
        }

        if (dp.containsKey(String.valueOf(n) + contCount + absentCount))
            return dp.get(String.valueOf(n) + contCount + absentCount);

        long currAns = 0;
        if (absentCount == 1 && contCount == 2) {
            long c1 = checkRecordHelper(n - 1, 0, absentCount, dp);
            currAns += ((c1 % mod)) % mod;
        } else if (absentCount == 1) {
            long c1 = checkRecordHelper(n - 1, contCount + 1, absentCount, dp);
            long c2 = checkRecordHelper(n - 1, 0, absentCount, dp);
            currAns += ((c1 % mod) + (c2 % mod)) % mod;
        } else if (contCount == 2) {
            long c1 = checkRecordHelper(n - 1, 0, absentCount + 1, dp);
            long c2 = checkRecordHelper(n - 1, 0, absentCount, dp);
            currAns += ((c1 % mod) + (c2 % mod)) % mod;
        } else {
            long c1 = checkRecordHelper(n - 1, contCount + 1, absentCount, dp);
            long c2 = checkRecordHelper(n - 1, 0, absentCount + 1, dp);
            long c3 = checkRecordHelper(n - 1, 0, absentCount, dp);
            currAns += ((c1 % mod) + (c2 % mod) + (c3 % mod)) % mod;
        }

        dp.put(String.valueOf(n) + contCount + absentCount, currAns);
        return currAns;
    }

    // 552. Student Attendance Record II - Using Memoization
    public int checkRecord(int n) {
        Map<String, Long> dp = new HashMap<>();
        return (int) (checkRecordHelper(n, 0, 0, dp) % mod);
    }


    //absent = 0, 1 | cont = 0,1,2

    //552. Student Attendance Record II - Using Iteration
    public int checkRecord2(int n) {
        if (n == 1) return 3;
        long[][][] dp = new long[n + 1][2][3];
        dp[1][1][2] = 1;
        dp[1][1][0] = 2;
        dp[1][1][1] = 2;
        dp[1][0][2] = 2;
        dp[1][0][0] = 3;
        dp[1][0][1] = 3;
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 3; k++) {
                    if (j == 1 && k == 2) {
                        dp[i][j][k] = dp[i - 1][1][0];
                    } else if (j == 1) {
                        dp[i][j][k] = (dp[i - 1][j][k + 1] % mod + dp[i - 1][j][0] % mod) % mod;
                    } else if (k == 2) {
                        dp[i][j][k] = (dp[i - 1][j + 1][0] % mod + dp[i - 1][j][0] % mod) % mod;
                    } else {
                        dp[i][j][k] = (dp[i - 1][j][k + 1] % mod + dp[i - 1][j + 1][0] % mod + dp[i - 1][j][0] % mod) % mod;
                    }
                }
            }
        }
        return (int) (dp[n][0][0]);
    }

    //1208. Get Equal Substrings Within Budget
    public int equalSubstring(String s, String t, int maxCost) {

        int ans = 0, currCost = 0, n = s.length(), st = 0, end = 0;

        while(end < n){
            currCost += Math.abs(s.charAt(end) - t.charAt(end));
            while(currCost > maxCost && st <= end){
                currCost -= Math.abs(s.charAt(st) - t.charAt(st));
                st++;
            }
            ans = Math.max(ans, end - st + 1);
            end++;
        }

        return ans;
    }

    //2419. Longest Subarray With Maximum Bitwise AND
    public int longestSubarray(int[] nums) {

        int n = nums.length, ans = 0;

        int maxNum = Arrays.stream(nums).max().getAsInt();

        int st = 0, end = 0;

        while(end < n){
            int curr = nums[end];
            boolean valid = true;
            int temp = maxNum;
            while(temp != 0){
                if((temp & (1)) == 1){
                    if((curr & 1) == 0){
                        valid = false;
                        break;
                    }
                }
                temp = (temp >> 1);
                curr = (curr >> 1);
            }
            if(!valid){
                st = end;
            }
            ans = Math.max(ans, end - st + 1);
            end++;
        }

        return ans;

    }

    //2486. Append Characters to String to Make Subsequence
    public int appendCharacters(String s, String t) {
        int n = s.length(), m = t.length();
        int j = 0;
        for(int i = 0; i < n ; i++){
            if(j < m && s.charAt(i) == t.charAt(j)){
                j++;
            }
        }
        return m - j;
    }

    //945. Minimum Increment to Make Array Unique
    public int minIncrementForUnique(int[] nums) {

        Arrays.sort(nums);
        int ans = 0, n = nums.length;
        for(int i = 1; i < n; i++){
            if(nums[i] <= nums[i-1]){
                int incr_by = Math.abs(nums[i] - nums[i-1]) + 1;
                nums[i] = nums[i] + incr_by;
                ans += incr_by;
            }
        }
        return ans;

    }

    private static class Work{
        int mc;
        int profit;
        int diff;

        public Work(int mc, int profit) {
            this.mc = mc;
            this.profit = profit;
        }

        public Work(int profit, int diff, int mc) {
            this.profit = profit;
            this.diff = diff;
        }
    }

  // 502. IPO
  public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;
        PriorityQueue<Work> pq = new PriorityQueue<>(new Comparator<Work>() {
            @Override
            public int compare(Work o1, Work o2) {
                return -1*Integer.compare(o1.profit,o2.profit);
            }
        });
        for(int i = 0; i < n; i++){
            Work work = new Work(capital[i], profits[i]);
            pq.add(work);
        }
        while (k > 0 && !pq.isEmpty()){
            Stack<Work> temp_stack = new Stack<>();
            while(!pq.isEmpty() && pq.peek().mc > w){
                Work work_temp = pq.poll();
                temp_stack.add(work_temp);
            }
            if(!pq.isEmpty()){
                Work best_doable_work = pq.poll();
                w += best_doable_work.profit;
                k--;
            }else{
                break;
            }
            while(!temp_stack.isEmpty()){
                Work temp_work2 = temp_stack.pop();
                pq.add(temp_work2);
            }
        }
        return w;
  }

  int bs(int[] arr, int n, int k){

        int st = 0, end = n - 1, mid;
        int ans = -1;

        while(st <= end){
            mid = (st + end)/2;
            if(arr[mid] <= k){
                ans = mid;
                st = mid + 1;
            }else{
                end = mid - 1;
            }
        }

        return ans;

  }

  //826. Most Profit Assigning Work
  public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {

        int n = profit.length, ans = 0, m = worker.length;
        Work[] works = new Work[n];
        int[] reProfits = new int[n];
        int[] reDiffs = new int[n];
        for(int i = 0; i < n ; i++){
            works[i] = new Work(profit[i], difficulty[i], -1);
        }
        Arrays.sort(works, new Comparator<Work>() {
            @Override
            public int compare(Work o1, Work o2) {
                return Integer.compare(o1.diff, o2.diff);
            }
        });
      for(int i = 0; i < n ; i++){
         reProfits[i] = works[i].profit;
         reDiffs[i] = works[i].diff;
      }

      int[] maxArr = new int [n];
      maxArr[0] = reProfits[0];
      for(int i = 1 ; i < n ; i++){
          maxArr[i] = Math.max(maxArr[i-1], reProfits[i]);
      }

      for(int i = 0; i < m ; i++){
          int idx = bs(reDiffs, n, worker[i]);
          if(idx != -1){
              int maxProfit = maxArr[idx];
              ans += maxProfit;
          }
      }
      return ans;

  }

    static int modAdd(int a, int b, int m)
    {
        return ((a % m) + (b % m)) % m;
    }

    // Function to perform Modular Subtraction
    static int modSub(int a, int b, int m)
    {
        return ((a % m) - (b % m) + m)
                % m; // Adding m to handle negative numbers
    }


    public int minAbsoluteSumDiff(int[] nums1, int[] nums2) {

        TreeSet<Integer> ts = new TreeSet<>();
        int n = nums1.length;
        for(int i = 0; i < n; i++){
            ts.add(nums1[i]);
        }
        int ans = 0;
        int mod = 1000000007;
        int replaceIndex = -1, replaceVal = -1;
        for(int i = 0; i < n; i++){
            int nearest = nums1[i];
            Integer floor = ts.floor(nums2[i]);
            Integer ceil = ts.ceiling(nums2[i]);
            if(floor != null && Math.abs(nearest - nums2[i]) > Math.abs(floor - nums2[i])){
                nearest = floor;
            }
            if(ceil != null && Math.abs(nearest - nums2[i]) > Math.abs(ceil - nums2[i])){
                nearest = ceil;
            }
            if(nearest != nums1[i] && replaceIndex == -1){
                replaceIndex = i;
                replaceVal = Math.abs(nums1[i] - nums2[i]) - Math.abs(nearest - nums2[i]);
            }else if(nearest != nums1[i]){
                if(replaceVal < (Math.abs(nums1[i] - nums2[i]) - Math.abs(nearest - nums2[i]))){
                    replaceIndex = i;
                    replaceVal = Math.abs(nums1[i] - nums2[i]) - Math.abs(nearest - nums2[i]);
                }
            }
        }

        for(int i = 0; i < n; i++){
            if(i == replaceIndex){
                ans = modAdd(ans, Math.abs(nums1[i] - nums2[i]) - replaceVal, mod);
            }else{
                ans = modAdd(ans, Math.abs(nums1[i] - nums2[i]), mod);
            }
        }

        return ans;
    }

}