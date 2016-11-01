package interview.leetcode.dvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A leetcode skyline problem. 
 * 
 * @author robeen
 *
 */
public class Skyline{
	
	
	public static void main(String[] args){
		testSkyline();
	}
	
	public static int[] getPoint(int x, int y){
		int[] temp = {x, y};
		return temp;
	}
	
	public static String pointToString(int[] point){
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for(int i = 0; i < point.length; ++i){
			sb.append(point[i]);
			if(i != point.length - 1)
				sb.append(",");
		}
		sb.append(")");
		return sb.toString();
	}
	
	/*
	public static String buildingToString(int[] building){
		return "(" + building[0] + "," + point[1] + ")";
	}
	*/
	
	public static void testSkyline(){
		int testCases = 10;
		Random rand = new Random(System.currentTimeMillis());
		for(int i = 0; i < testCases; ++i){
			int numBuildings = rand.nextInt(20) + 1;
			int[][] buildings = new int[numBuildings][3];
			System.out.println("Test Case # " + i);
			System.out.print("Buildings: ");
			for(int L = 0; L < numBuildings; ++L){
				int R = L + rand.nextInt(10) + 1; //(R > L)
				int H = rand.nextInt(Integer.MAX_VALUE);
				buildings[L][0] = L;
				buildings[L][1] = R;
				buildings[L][2] = H;
				System.out.print(pointToString(buildings[L]));
			}
			System.out.println();
			
			List<int[]> skyline = computeSkyline(buildings);
			System.out.print("Skyline: " );
			for(int[] s : skyline){
				System.out.print(pointToString(s));
			}
			System.out.println();
			System.out.println();
		}
	}
	
	public static void testMerge(){
		//int testCases = 10;
		int a = 0, b = 5;
		int x = 1;
		int[] yArray = {2, 5, 7};
		int h1 = 4;
		int[] h2Array = {2, 4, 8};
		// SL1 = {{a, h1}, {b, 0}}
		// SL2 = {{x, h2}, {y, 0}}
		for(int y : yArray){
			if(y < b) System.out.println("Case: y < b");
			else if(y == b) System.out.println("Case: y = b");
			else System.out.println("Case: y > b");
			for(int h2 : h2Array){
				if(h2 < h1) System.out.println("Sub-Case: h2 < h1");
				else if(h2 == h1) System.out.println("Sub-Case: h2 = h1");
				else System.out.println("Sub-Case: h2 > h1");
				List<int[]> SL1 = new ArrayList<int[]>();
				SL1.add(getPoint(a, h1));
				SL1.add(getPoint(b, 0));
				System.out.print("Point Skyline1: ");
				for(int[] s : SL1){
					System.out.print(pointToString(s));
				}
				System.out.println();
				
				List<int[]> SL2 = new ArrayList<int[]>();
				SL2.add(getPoint(x, h2));
				SL2.add(getPoint(y, 0));
				
				System.out.print("Point Skyline2: ");
				for(int[] s : SL2){
					System.out.print(pointToString(s));
				}
				System.out.println();
				
				List<int[]> SL = merge(SL1, SL2);
				System.out.print("Point Merged Skyline: ");
				for(int[] s : SL){
					System.out.print(pointToString(s));
				}
				System.out.println();
			}
			
		}
		
		
	}
	
	/**
	 * Given an array of buildings, each in the format: (L, R, H). <br>
	 * Imagine a building is just a box in 2D space, L is the left x-axis,
	 * R is the right x-axis, and H is the height. <br>
	 * We want to compute the skyline of these buildings.
	 * 
	 * @param buildings The input buildings
	 * @return A list of skylines
	 */
	public static List<int[]> computeSkyline(int[][] buildings){
		List<int[]> result = new ArrayList<int[]>();
		if(buildings == null || buildings.length == 0) return result;
		return computeSkyline(buildings, 0, buildings.length - 1);
	}
	
	/**
	 * A recursive routine. 
	 * Given the buildings sub-array [from .., to] (both inclusive), compute the skyline.
	 * @param buildings
	 * @param from
	 * @param to
	 * @return
	 */
	private static List<int[]> computeSkyline(int[][] buildings, int from, int to){
		List<int[]> result = new ArrayList<int[]>();
		if(from == to){
			result.add(getPoint(buildings[from][0], buildings[from][2]));
			result.add(getPoint(buildings[from][1], 0));
			return result;
		}
		int mid = from + (to - from) / 2;
		List<int[]> LS1 = computeSkyline(buildings, from, mid);
		List<int[]> LS2 = computeSkyline(buildings, mid + 1, to);
		return merge(LS1, LS2);
	}
	
	/**
	 * A merge routine for the skyline algorithm.
	 * Given two arrays of skylines, merge them into one skyline.
	 * This is sort of like a merge sort, but the merging process would 
	 * be much more complicated. <br>
	 * @param SL1 The first skyline
	 * @param SL2 The second skyline
	 * @return The merged skyline
	 */
	public static List<int[]> merge(List<int[]> SL1, List<int[]> SL2){
		List<int[]> result = new ArrayList<int[]>();
		int len1 = SL1.size(), len2 = SL2.size();
		int p1 = 0, p2 = 0, H1 = 0, H2 = 0;
		int[] cur = null;
		// A boolean parameter to record whether the cur value is from
		// SL1 or SL2
		boolean fromSL1 = true;
		while(p1 < len1 && p2 < len2){
			int[] s1 = SL1.get(p1);
			int[] s2 = SL2.get(p2);
			if(s1[0] < s2[0]){
				fromSL1 = true;
				cur = s1;
				H1 = cur[1];
				++p1;
			}
			else if(s1[0] > s2[0]){
				fromSL1 = false;
				cur = s2;
				H2 = cur[1];
				++p2;
			}
			else{
				if(s1[1] > s2[1]){
					fromSL1 = true;
					cur = s1;
				}
				else{
					fromSL1 = false;
					cur = s2;
				}
				H1 = s1[1];
				H2 = s2[1];
				++p1; ++p2;
			}
			int h = 0;
			if(fromSL1) h = Math.max(cur[1], H2);
			else h = Math.max(cur[1], H1);
			if(result.isEmpty() || h != result.get(result.size() - 1)[1]){
				result.add(getPoint(cur[0], h));
			}
		}
		
		while(p1 < len1) result.add(SL1.get(p1++));
		while(p2 < len2) result.add(SL2.get(p2++));
		
		return result;
	}
}