package fr.neatmonster.nocheatplus.test;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.neatmonster.nocheatplus.utilities.ActionAccumulator;


public class TestActionAccumulator {
	
	/**
	 * Fill the accumulator with numbers from 1 to buckets * capacity.
	 * @param acc
	 */
	private void fill(ActionAccumulator acc) {
		int buckets = acc.numberOfBuckets();
		int capacity = acc.bucketCapacity();
		// Add different amounts to better check consistency.
		for (int i = 1; i <= buckets * capacity; i ++){
			acc.add(i);
		}
	}
	
	/**
	 * Check if values and counts are as if fill was called on empty acc.
	 * @param acc
	 */
	private void checkIncreasingValues(ActionAccumulator acc) {
		int buckets = acc.numberOfBuckets();
		int capacity = acc.bucketCapacity();
		for (int i = 0; i < buckets; i++){
			if (acc.bucketCount(i) != capacity) fail("Bad capacity at " + i + ": " + acc.bucketCount(i) + " / " + capacity);
			int start = (buckets - 1 - i) * capacity + 1;
			int end = start + capacity - 1;
			int expect = end * (end + 1) / 2 - (start - 1) * ((start - 1) + 1) / 2;
			if (acc.bucketScore(i) != (float) expect)  fail("Bad value at bucket " + i + ": " + acc.bucketScore(i) + " / " + expect);
		}
		if (acc.count() != buckets * capacity) fail("Total count.");
		if (acc.score() != buckets * capacity * (buckets * capacity + 1) / 2) fail ("Total score.");
	}
	
	@Test
	public void testFill(){
		ActionAccumulator acc = new ActionAccumulator(50, 10);
		fill(acc);
		checkIncreasingValues(acc);
	}
	
	@Test
	public void testClear(){
		ActionAccumulator acc = new ActionAccumulator(50, 10);
		fill(acc);
		acc.clear();
		if (acc.count() != 0) fail("Expect 0 count after clear, got: " + acc.count());
		if (acc.score() != 0) fail("Expect 0 score after clear, got: " + acc.score());
		for (int i = 0; i < acc.numberOfBuckets(); i++){
			if (acc.bucketCount(i) != 0) fail("Expect 0 count at " + i + " after clear, got: " + acc.bucketCount(i));
			if (acc.bucketScore(i) != 0) fail("Expect 0 score at " + i + " after clear, got: " + acc.bucketScore(i));
		}
	}
	
}
