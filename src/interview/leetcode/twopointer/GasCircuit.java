package interview.leetcode.twopointer;

/**
 * LeetCode 134. Gas Station (https://leetcode.com/problems/gas-station/) <br>
 * 
 * There are N gas stations along a circular route, where the amount of gas at station i is gas[i]. <br>
 * You have a car with an unlimited gas tank and it costs cost[i] of gas to travel 
 * from station i to its next station (i+1). You begin the journey with an empty 
 * tank at one of the gas stations. <br>
 * 
 * Return the starting gas station's index if you can travel around the circuit once, otherwise return -1.
 * 
 * @author Robeen Lai
 * @email longbin.lai@gmail.com
 *
 */
public class GasCircuit {
	
	public static void main(String[] args){
		int[] gas = {4, 5, 3, 100};
		int[] cost = {5, 6, 4, 7};
		System.out.println(canCompleteCircuit(gas, cost));
	}
	
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        if(gas == null || gas.length == 0 ||
            cost == null || cost.length == 0 || 
            gas.length != cost.length) return -1;
            
        int len = gas.length;
        if(len == 1){
            return gas[0] >= cost[0] ? 0 : -1;   
        }
        
        int p1 = 0, p2 = 1;
        int left = gas[p1] - cost[p1];
        int res = -1;
        while(true){
            if(left < 0){
                left += cost[p1] - gas[p1];
                p1 = next(p1, len);
                if(p1 == 0) {
                     break;  
                }
                if(p1 == p2){
                    left += gas[p1] - cost[p1];
                    p2 = next(p2, len);
                }
            }
            else{
                left += gas[p2] - cost[p2];
                p2 = next(p2, len);
                if(p1 == p2 && left >= 0) {
                    res = p1;
                    break;
                }
            }
        }
        return res;
        
    }
    
    public static int next(int i, int n){
        return (i == n - 1) ? 0 : i + 1;
    }
}