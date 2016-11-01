package interview.leetcode.math;


public class SuperUglyNumber {
	
	public static void main(String[] args){
		int[] primes = {2,3,7,13,17,23,31,41,43,47};
		int n = 45;
		
		System.out.println(nthSuperUglyNumber(n, primes));
	}
	
	public static int nthSuperUglyNumber(int n, int[] primes) {
        if(primes == null || n <= 0 || primes.length == 0){
            // It is an error
            return -1;
        }
        int len = primes.length;
        int[] a = new int[len];
        int[] elem = new int[n];
        elem[0] = 1;
        int cur = Integer.MAX_VALUE;
        
        for(int i = 1; i < n; ++i){
        	cur = Integer.MAX_VALUE;
        	for(int j = 0; j < len; ++j){
        		int mul = primes[j] * elem[a[j]];
        		if(cur > mul){
        			cur = mul;
        			//curIdx = j;
        		}
        	}
        	elem[i] = cur;
        	
        	for(int j = 0; j < len; ++j){
        		if(primes[j] * elem[a[j]] == cur){
        			++a[j];
        		}
        	}
        	
        	System.out.println("i = " + i + "; res = " + cur); 
        }
        
        return elem[n - 1];
    }
	
}