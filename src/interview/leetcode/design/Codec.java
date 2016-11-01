package interview.leetcode.design;
import java.util.ArrayList;
import java.util.List;

public class Codec {
	
	public static void main(String[] args){
		String[] strs = {"123", "aaa", "bb", "", "cc", "", "123xwe``-=Z"};
		List<String> input = new ArrayList<String>();
		for(int i = 0; i < strs.length; ++i){
			input.add(strs[i]);
		}
		Codec codec = new Codec();
		String enc = codec.encode(input);
		List<String> dec = codec.decode(enc);
		for(String str : dec){
			System.out.println(str);
		}
	}
    
private final static int[] SS = {0, Character.SIZE, 2 * Character.SIZE, 3 * Character.SIZE};
    
    private char[] toCharArray(int size){
        char[] res = new char[4];
        int mask = (1 << Character.SIZE) - 1;
        for(int i = 0; i < 4; ++i){
            res[i] = (char) ((size >>> SS[i]) & mask);
        }
        return res;
    }
    
    private int nextInteger(char[] chars){
        int result = 0;
        for(int i = 0; i < 4; ++i){
            result |= (((int) chars[i]) << SS[i]);
        }
        return result;
    }

    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        if(strs == null || strs.size() == 0) return "";
        StringBuilder sb = new StringBuilder();
        for(String str : strs){
            sb.append(toCharArray(str.length()));
            if(str.length() != 0) sb.append(str);
        }
        return sb.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String str) {
        List<String> result = new ArrayList<String>();
        if(str == null || str.length() == 0) return result;
        int curIndex = 0, curLen = 0;
        while(curIndex < str.length()){
            char[] chars = new char[4];
            for(int i = 0; i < 4; ++i){
                chars[i] = str.charAt(curIndex++);
            }
            curLen = nextInteger(chars);
            result.add(str.substring(curIndex, curIndex + curLen));
            curIndex += curLen;
        }
        return result;
    }
}