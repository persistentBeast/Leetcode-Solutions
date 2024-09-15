package com.abhishek.dsa;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MedianFinder {

  PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
  PriorityQueue<Integer> minHeap = new PriorityQueue<>();
  boolean even;
  boolean empty;

  public MedianFinder() {
    empty = true;
    even = true;
  }

  public void addNum(int num) {
    if (empty) {
      maxHeap.offer(num);
      empty = false;
      even = !even;
      return;
    }
    if (even) {
      if (num >= maxHeap.peek() && num <= minHeap.peek()) {
        maxHeap.offer(num);
      } else if (num >= maxHeap.peek() && num >= minHeap.peek()) {
        maxHeap.offer(minHeap.poll());
        minHeap.offer(num);
      } else {
        maxHeap.offer(num);
      }
    } else {
      if (num <= maxHeap.peek()) {
        minHeap.offer(maxHeap.poll());
        maxHeap.offer(num);
      } else {
        minHeap.offer(num);
      }
    }
    even = !even;
  }

  public double findMedian() {
    if (even) {
      return (maxHeap.peek() + minHeap.peek()) / 2.0;
    } else {
      return maxHeap.peek();
    }
  }
}
