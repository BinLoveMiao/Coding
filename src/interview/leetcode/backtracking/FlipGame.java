
package interview.leetcode.backtracking;

public class FlipGame{
	
	public static void main(String[] args){
		String s = "++++-+";
		System.out.println(new FlipGame().canWin(s));
	}
	
	private void flip(char[] chars, int i){
        if(chars[i] == '+'){
            chars[i] = '-';
            chars[i - 1] = '-';
        }
        else{
            chars[i] = '+';
            chars[i - 1] = '+';
        }
    }
    
    public boolean canWin(char[] chars, int len){
        boolean isPlus = chars[0] == '+';
        boolean result = false;
        // Clearly, we can directed jump to where there is a one.
        // and we can implement thus optimization by using a bit map.
        // we always track where there is a "+"
        for(int i = 1; i < len; ++i){
            if(isPlus && chars[i] == '+'){
                flip(chars, i);
                if(!canWin(chars, len)) {
                	// Originally, there is a bug when I return true from here.
                	// as it is returned with restoring the status of the char array.
                	// This is a clear mistake that we have to avoid in backtracking programming.
                	result = true;
                }
                flip(chars, i);
                if(result) return true;
            }
            if(chars[i] == '-') isPlus = false;
            else isPlus = true;
        }
        return result;
    }
    
    public boolean canWin(String s) {
        if(s == null || s.length() == 0) return false;
        return canWin(s.toCharArray(), s.length());
    }
	
}