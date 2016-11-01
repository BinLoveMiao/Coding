package interview.leetcode.design;
import java.util.Stack;

public class MaxStack {
	private Stack<Integer> stack;
	private int max;
	
	public MaxStack(){
		this.stack = new Stack<Integer>();
		this.max = Integer.MIN_VALUE;
	}
	
	public void push(int elem){
		if(elem >= this.max){
			this.stack.push(max);
			max = elem;
		}
		this.stack.push(elem);
	}
	
	public int pop(){
		int res = 0;
		if(stack.peek() == max){
			res = stack.pop();
			max = stack.peek();
			stack.pop();
		}
		else{
			res = stack.pop();
		}
		return res;
	}
	
	public int peek(){
		return stack.peek();
	}
	
	public boolean empty(){
		return stack.empty();
	}
	
	public int max(){
		return this.max;
	}
	
	public Stack<Integer> getStack(){
		return this.stack;
	}
}