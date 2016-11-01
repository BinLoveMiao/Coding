package interview.leetcode.tree;
import java.util.Arrays;

public class Bulb {
	
	private boolean[] bulbs;
	private int[] freq;
	private int n;
	
	public static void main(String[] args){
		boolean[] tests = new boolean[10];
		for(int i = 0; i < tests.length; ++i){
			tests[i] = false;
		}
		Bulb bulb = new Bulb(tests);
		for(int i = 0; i < 10; ++i){
			System.out.println(i + ": " + bulb.getBulb(i));
		}
		
		System.out.println();
		
		bulb.flip(0, 3);
		
		for(int i = 0; i < 10; ++i){
			System.out.println(i + ": " + bulb.getBulb(i));
		}
		
		System.out.println();
		
		bulb.flip(2,  7);
		
		for(int i = 0; i < 10; ++i){
			System.out.println(i + ": " + bulb.getBulb(i));
		}
		
		System.out.println();
		
		bulb.flip(3,  6);
		
		for(int i = 0; i < 10; ++i){
			System.out.println(i + ": " + bulb.getBulb(i));
		}
		
		System.out.println();
		
		bulb.flip(1,  8);
		
		for(int i = 0; i < 10; ++i){
			System.out.println(i + ": " + bulb.getBulb(i));
		}
		
		System.out.println();
	}
	
	public Bulb(boolean[] b){
		bulbs = Arrays.copyOf(b, b.length);
		n = b.length;
		int h = (int) Math.ceil(Math.log(n) / Math.log(2)) + 1;
		int size = (int) Math.ceil((1 << h) + 1);
		freq = new int[size];
	}
	
	public void flip(int start, int end){
		assert(bulbs != null && n != 0);
		flipRecur(0, n - 1, start, end, 0);
	}
	
	private void flipRecur(int ts, int te, int qs, int qe, int ti){
		if(ts > qe || te < qs) return;
		if((ts >= qs && te <= qe)){
			freq[ti] += 1;
		}
		else{
			int mid = ts + (te - ts) / 2;
			flipRecur(ts, mid, qs, qe, left(ti));
			flipRecur(mid + 1, te, qs, qe, right(ti));
		}
	}
	
	public boolean getBulb(int index){
		assert(index >= 0 && index < n);
		int sumFlips = 0;
		int ts = 0, te = n - 1, ti = 0;
		int mid = 0;
		while(ts < te){
			sumFlips += freq[ti];
			mid = ts + (te - ts) / 2;
			if(index <= mid){
				te = mid;
				ti = left(ti);
			}
			else{
				ts = mid + 1;
				ti = right(ti);
			}
		}
		sumFlips += freq[ti]; // Add the leaves
		if((sumFlips & 1) == 0) return this.bulbs[index];
		else return !this.bulbs[index];
	}
	
	
	public static int left(int ti){
		return (ti << 1) + 1;
	}
	
	public static int right(int ti){
		return (ti << 1) + 2;
	}
	
	public static int parent(int ti){
		if(ti == 0) return -1;
		return (ti - 1) >> 1;
	}
	
	
}