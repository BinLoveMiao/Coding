package interview.leetcode.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * LeetCode 282. Expression Add Operators
 * (https://leetcode.com/problems/expression-add-operators/) <br>
 * 
 * Given a string that contains only digits 0-9 and a target value, 
 * return all possibilities to add binary operators (not unary) +, -, 
 * or * between the digits so they evaluate to the target value.
 * 
 * This problem has several tricky corner cases, and it is really hard. 
 * We think of this in a recursion way. Suppose that we are in step i (num.charAt(i)),
 * and we have the following choices to add operators prior to i: <br>
 * (1) No operator;  <br>
 * (2) Add "+"; <br>
 * (3) Add "-"; <br>
 * (4) Add "*"; <br>
 * 
 * Let's use the following example to clarify the parameters: calc, val, and prev, where <br>
 * 
 * calc: Refers to the part that has finished computing.<br>
 * val : The most recent number. <br>
 * prev: The result of the product prior to the most recent number. <br>
 *  
 * 
 * e.g. 1 + 2 * 3 [4], we now decide to add operation prior to 4. <br>
 * 
 * Before we add operators to 4, we have: <br>
 * calc:  As we have no idea how 2 * 3 further go, 
 * the only part that is known for sure is "1". Therefore, calc = 1. <br>
 * val:  val = 3, as it is the number right before 4. <br>
 * prev:  prev = 2. <br>
 * 
 * and for each possible branch, we figure out how calc, val, and prev change. <br>
 * 
 * (1) No operator: calc = calc, val = 10 * val + current_digit = 34, prev = prev;
 * (2) Add "+": calc = calc + prev * val = 7. As we know the value of the part "1 + 2 * 3" must be settled.
 * val = 4, prev = 1 (Currently there are no prior product);
 * (3) Add "-": calc = calc + prev * val = 7, similary to adding "+", val = -4 (carefully), 
 * prev = 1;
 * (4) Add "*": calc = calc (we still have no idea when 2 * 3 * 4 ends), prev = prev * val = 6, val = 4.
 * 
 * 
 * @author Robeen Lai
 * @email longbin.lai@gmail.com
 *
 */
public class AddOperators{
	
	public static void main(String[] args){
		String num = "1000009";
		int target = 9;
		AddOperators opr = new AddOperators();
		List<String> result = opr.addOperators(num, target);
		for(String s : result){
			System.out.println(s);
		}
	}
    
    public List<String> addOperators(String num, int target) {
        List<String> result = new ArrayList<String>();
        if(num == null || num.length() == 0) return result;
        int len = num.length();
        char[] path = new char[2 * len];
        int val = digit(num.charAt(0));
        path[0] = num.charAt(0);
        addOpr(num, 1, path, 1, 0L, val, 1L, target, result);
        return result;
        
    }
    
    // Corner case: long type for calc, val and prev to prevent overflow.
    private void addOpr(String num, int level, char[] path, int count, long calc, 
    		long val, long prev, int target, List<String> result){
    	if(level == num.length()){
    		// calc + val * prev
    		if(target == calc + val * prev){
    			result.add(String.valueOf(path, 0, count));
    		}
    		return;
    	}
    	char c = num.charAt(level);
    	int d = digit(c);
    	// Branch I, add no operator
    	if(val != 0){ // Corner case: to avoid number that has leading zeros: like 012
    		path[count] = c;
    		// Corner case: if val < 0, we should calculate next val via val * 10 - d.
    		long nextVal = val >= 0 ? val * 10 + d : val * 10 - d;
    		addOpr(num, level + 1, path, count + 1, calc, nextVal, prev, target, result);
    	}
    	// Branch II, add + operator
    	path[count] = '+';
    	path[count + 1] = c;
    	addOpr(num, level + 1, path, count + 2, calc + prev * val, d, 1, target, result);
    	// Branch III, add - operator
    	path[count] = '-';
    	path[count + 1] = c;
    	addOpr(num, level + 1, path, count + 2, calc + prev * val, -d, 1, target, result);
    	// Branch IV, add * operator
    	path[count] = '*';
    	path[count + 1] = c;
    	addOpr(num, level + 1, path, count + 2, calc, d, prev * val, target, result);
    }
    
    private static int digit(char c){
        return (int) (c - '0');
    }
}