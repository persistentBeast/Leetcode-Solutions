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

}