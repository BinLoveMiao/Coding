package interview.leetcode.design;
import java.util.EmptyStackException;

public class MaxQueue{
	
	private MaxStack stack1; 
	private MaxStack stack2;
	
	public MaxQueue(){
		this.stack1 = new MaxStack();
		this.stack2 = new MaxStack();
	}
	
	public void enQueue(int elem){
		this.stack1.push(elem);
	}
	
	public int deQueue(){
		if(this.empty()) throw new EmptyStackException();
		if(stack2.empty()) 
			while(!stack1.empty()) stack2.push(stack1.pop());
		return stack2.pop();
	}
	
	public int peek(){
		if(this.empty()) throw new EmptyStackException();
		if(stack2.empty()) 
			while(!stack1.empty()) stack2.push(stack1.pop());
		return stack2.peek();
	}
	
	public boolean empty(){
		return this.stack1.empty() && this.stack2.empty();
	}
	
	public int max(){
		return Math.max(this.stack1.max(), this.stack2.max());
	}
}