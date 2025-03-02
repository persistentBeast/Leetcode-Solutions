package com.abhishek.dsa;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

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

    public int trapRainWater(int[][] heightMap) {

        int n = heightMap.length;
        int m = heightMap[0].length;
        int ans = 0;
        if(n == 1 || n == 2 || m == 1 || m == 2) return ans;

        int[][] heightMapDp = new int[n][m];
        for(int i = 0; i < n; i++){
            int [] arr = new int[m];
            heightMapDp[i] = arr;
        }

        for(int i = 1; i <= n - 1; i++){
            int prevMax = heightMap[i][0];
            for(int j = 1; j <= m - 1; j++){
                heightMapDp[i][j] = prevMax;
                prevMax = Math.max(prevMax, heightMap[i][j]);
            }
        }

        for(int i = 1; i <= n - 1; i++){
            int prevMax = heightMap[i][n-1];
            for(int j = m-2; j >= 0; j--){
                heightMapDp[i][j] = Math.min(heightMapDp[i][j], prevMax);
                prevMax = Math.max(prevMax, heightMap[i][j]);
            }
        }

        for(int j = 1; j <= m - 1; j++){
            int prevMax = heightMap[0][j];
            for(int i = 1; i < n; i++){
                heightMapDp[i][j] = Math.min(heightMapDp[i][j], prevMax);
                prevMax = Math.max(prevMax, heightMap[i][j]);
            }
        }

        for(int j = 1; j <= m - 1; j++){
            int prevMax = heightMap[n-1][j];
            for(int i = n-2; i >= 0; i--){
                heightMapDp[i][j] = Math.min(heightMapDp[i][j], prevMax);
                prevMax = Math.max(prevMax, heightMap[i][j]);
            }
        }

        for(int i = 1; i < n - 1; i++){
            for(int j = 1; j < m - 1; j++){
                heightMapDp[i][j] = Math.min(heightMapDp[i][j], Math.min(heightMapDp[i-1][j], heightMapDp[i][j-1]));
            }
        }

        for(int i = n-2; i > 0; i--){
            for(int j = m-2; j > 0; j--){
                heightMapDp[i][j] = Math.min(heightMapDp[i][j], Math.min(heightMapDp[i+1][j], heightMapDp[i][j+1]));
            }
        }

        for(int i = 1; i < n - 1; i++){
            for(int j = 1; j < m - 1; j++){
                if(heightMap[i][j] < heightMapDp[i][j]){
                    ans += (heightMapDp[i][j] - heightMap[i][j]);
                }
            }
        }

        return ans;

    }

    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
        int n = nums.length;
        Map<Integer, Integer> groups = new HashMap<>();
        Map<Integer, PriorityQueue<Integer>> groupsOrdered = new HashMap<>();
        int ctr = 0;
        int [] arr = Arrays.copyOf(nums, n);
        Arrays.sort(arr);
        groups.put(arr[0], ctr);
        PriorityQueue<Integer> init = new PriorityQueue<>();
        init.add(arr[0]);
        groupsOrdered.put(ctr, init);
        for(int i = 1; i < n; i++){
            if(arr[i] - arr[i-1] <= limit){
                groups.put(arr[i], ctr);
                groupsOrdered.get(ctr).add(arr[i]);
            }else{
                ctr++;
                groups.put(arr[i], ctr);
                PriorityQueue<Integer> init2 = new PriorityQueue<>();
                init2.add(arr[i]);
                groupsOrdered.put(ctr, init2);
            }
        }

        for(int i = 0
            ; i < n; i++){
            nums[i] = groupsOrdered.get(groups.get(nums[i])).poll();
        }
        return nums;
    }

    public class ListNode {
      int val;
     ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

    public ListNode deleteDuplicates(ListNode head) {

        return deleteDuplicatesHelper(head, null);

    }

    public ListNode deleteDuplicatesHelper(ListNode head, ListNode prev) {

        if(head == null ) return null;
        boolean delCurrNode = false;
        if(head.next != null && head.next.val == head.val){
            delCurrNode = true;
        }
        ListNode makeRight = deleteDuplicatesHelper(head.next, head);
        if((prev != null && head.val == prev.val) || delCurrNode){
            return makeRight;
        }
        head.next = makeRight;
        return head;
    }

    // check why this is wrong
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        int gp = 0;
        int[] dp = new int[n];

        Arrays.fill(dp, -1);

        for(int i = 0; i < n ; i++){
            for(int j = 0; j < n; j++){
                if( i != j && isConnected[i][j] == 1){
                    if(dp[i] != -1 && dp[j] == -1){
                        dp[i] = dp[j];
                    }else if(dp[i] == -1 && dp[j] != -1){
                        dp[j] = dp[i];
                    }else if(dp[i] == -1 && dp[j] == -1){
                        gp++;
                        dp[i] = gp;
                        dp[j] = gp;
                    }else{
                        if(dp[i] != dp [j]){
                            for(int k = 0; k < n; k++){
                                if(dp[k] == dp[j]){
                                    dp[k] = dp[i];
                                }
                            }
                        }
                    }
                }
            }
            if(dp[i] == -1){
                gp++;
                dp[i] = gp;
            }
        }

        Set<Integer> groups = new HashSet<>();
        for(int i = 0; i < n; i++){
            groups.add(dp[i]);
        }

        return groups.size();
    }

    public int findCircleNum2(int[][] isConnected) {
        int n = isConnected.length;
        int gp = 0;
        int[] visited = new int[n];

        Arrays.fill(visited, -1);

        for(int i = 0; i < n ; i++){
            if(visited[i] == -1){
                gp++;
                dfsProvinces(visited, i, isConnected);
            }
        }

        return gp;

    }

    private void dfsProvinces(int[] visited, int node, int[][] isConnected){
        visited[node] = 1;
        for(int j = 0; j < visited.length; j++){
            if(node != j && isConnected[node][j] == 1 && visited[j] != -1){
                dfsProvinces(visited, j, isConnected);
            }
        }
    }

    public int minTaps(int n, int[] ranges) {

        int[] dp = new int[n];
        dp[n-1] = 0;

        for(int i = 1; i < n ; i++){
            int left = Math.max(0, i - ranges[i]);
            ranges[left] = Math.max(ranges[left], i + ranges[i] - left);
        }

        for(int i = n - 2; i>= 0; i--){
            int minAns = -1;
            for(int j = 1; i + j <= Math.min(n - 1, ranges[i]) ; j++){
                minAns = Math.min(minAns, 1 + dp[i + j]);
            }
            dp[i] = minAns;
        }

        return dp[0];

    }

    public String smallestEquivalentString(String s1, String s2, String baseStr) {

        int[] dp = new int[26];
        int n = s1.length(), m = baseStr.length();
        StringBuilder ans = new StringBuilder();
        for(int i = 0; i < dp.length; i++){
            dp[i] = i + 97;
        }

        for(int i = 0; i < n; i++){
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            if(dp[c1 - 97] < dp[c2 - 97]){
                helperSmallestEquivalentString(dp, c2, c1);
            }else{
                helperSmallestEquivalentString(dp, c1, c2);
            }
        }

        for(int i = 0; i < m; i++){
           ans.append((char) dp[baseStr.charAt(i) - 97]);
        }

        return ans.toString();
    }

    private void helperSmallestEquivalentString(int[] dp, char oldC, char newC){
        for(int i = 0; i < 26; i++){
            if(i != oldC - 97 && dp[i] == dp[oldC - 97]) dp[i] = dp[newC - 97];
        }
        dp[oldC - 97] = dp[newC - 97];
    }

    public int minInsertions(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for(int i = 0; i<n; i++){
            for(int j = 0; j < n; j++){
                dp[i][j] =-1;
            }
        }

        return minInsertionsHelper(dp, 0, n - 1, s);

    }

    public int minInsertionsHelper(int[][]dp, int i, int j, String s) {
        if(dp[i][j] != -1) return dp[i][j];

        if(i == j){
            dp[i][j] = 0;
            return 0;
        }

        if(s.charAt(i) == s.charAt(j)){
            dp[i][j] = minInsertionsHelper(dp, i+1, j-1,s);
        }else{
            dp[i][j] = 1 + Math.min(minInsertionsHelper(dp, i+1, j,s), minInsertionsHelper(dp, i, j - 1,s));
        }

        return dp[i][j];

    }

    public int strangePrinter(String s) {
        StringBuilder sanitised = new StringBuilder();
        int n = s.length();
        sanitised.append(s.charAt(0));

        for(int i = 1; i < n; i++){
            if(s.charAt(i) != s.charAt(i - 1)){
                sanitised.append(s.charAt(i));
            }
        }

        String S = sanitised.toString();
        int m = S.length();

        int[][] dp = new int[m][m];
        for(int i = 0; i<m; i++){
            for(int j = 0; j < m; j++){
                dp[i][j] = -1;
            }
        }

        return strangePrinterHelper(dp, 0, m - 1, S);
    }

    public int strangePrinterHelper(int[][]dp, int i, int j, String s) {
        if(i > j){
            return 0;
        }

        if(dp[i][j] != -1) return dp[i][j];

        int minTurns = 1 + strangePrinterHelper(dp, i + 1, j, s);

        for(int k = i + 1; k <= j; k++){
            if(s.charAt(k) == s.charAt(i)){
                minTurns = Math.min(minTurns, strangePrinterHelper(dp, i, k - 1, s) + strangePrinterHelper(dp, k + 1, j, s));
            }
        }

        return dp[i][j] = minTurns;

    }

    public int uniqueLetterString(String s) {

        int n = s.length();
        int ans = 0;
        Map<Integer, List<Integer>> occr = new HashMap<>();

        for(int i = 0; i < n; i++){
            char c1 = s.charAt(i);
            if(occr.containsKey((int) c1)){
                occr.get((int) c1).add(i);
            }else{
                List<Integer> newL = new ArrayList<>();
                newL.add(i);
                occr.put((int) c1, newL);
            }
        }

        for(List<Integer> arr : occr.values()){
            for(int i = 0; i < arr.size(); i++){
                int prev = i == 0 ? -1 : arr.get(i-1);
                int next = i == arr.size() -1 ? n-1 : arr.get(i + 1);
                ans += (arr.get(i) - prev + 1) * (next - arr.get(i));
            }
        }

        return ans;
    }

    public int numSubmatrixSumTarget(int[][] matrix, int target) {
        int n = matrix.length, m = matrix[0].length;
        int ans = 0;

        for(int k = n; k > 0; k--){
            int [][] prefixSum = new int[k][m];
            prefixSum[k-1][0] = matrix[k-1][0];

            for(int i = k - 1; i >= 0; i--){
                Map<Integer, Integer> lastSeen = new HashMap<>();
                lastSeen.put(0, 1);
                for(int j = 0; j < m; j++){
                    if(j == 0){
                        if(i == k - 1){
                            prefixSum[i][j] = matrix[i][j];
                        }else{
                            prefixSum[i][j] = matrix[i][j] + prefixSum[i+1][j];
                        }
                    }else{
                        if( i == k - 1){
                            prefixSum[i][j] = matrix[i][j] + prefixSum[i][j-1];
                        }else{
                            prefixSum[i][j] = matrix[i][j] + prefixSum[i+1][j] + prefixSum[i][j-1] - prefixSum[i + 1][j-1];
                        }
                    }
                    ans += lastSeen.getOrDefault(prefixSum[i][j] - target, 0);
                    lastSeen.put(prefixSum[i][j], lastSeen.getOrDefault(prefixSum[i][j], 0) + 1);
                }
            }
        }
        return ans;
    }

    public int[] canSeePersonsCount(int[] heights) {

        int n = heights.length;
        Stack<Pair> st= new Stack<>();
        int[] ans = new int[n];
        ans[n-1] = 0;
        st.add(new Pair(heights[n-1], n - 1));

        for(int i = n - 2; i >= 0; i--){

            int myAns = 0;
            while(!st.empty() && st.peek().l <= heights[i]){
                st.pop();
                myAns++;
            }

            ans[i] = myAns + (st.isEmpty() ? 0 : 1);
            st.add(new Pair(heights[i], i));
        }

        return ans;
    }


}


