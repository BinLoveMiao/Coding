package interview.leetcode.dvc;

public class DecodeString {
	
	public static void main(String[] args){
		String test1 = "ac";
		String test2 = "3[a]";
		String test3 = "3[ab]aa2[ac]";
		String test4 = "a2[ab2[a3[b]]]";
		
		DecodeString decode = new DecodeString();
		System.out.println(decode.decode(test1, 0, test1.length()));
		System.out.println(decode.decode(test2, 0, test2.length()));
		System.out.println(decode.decode(test3, 0, test3.length()));
		System.out.println(decode.decode(test4, 0, test4.length()));
		
	}

	public String decode(String s, int start, int end) {
		if (s == null || end <= start)
			return "";
		StringBuilder sb = new StringBuilder();
		boolean inBracket = false;
		int nextStart = 0, dup = 0;
		int numLeft = 0;

		for (int i = start; i < end; ++i) {
			char c = s.charAt(i);
			if (!inBracket) {
				if (isDigit(c)) {
					dup = dup * 10 + (int) (c - '0');
				} else if (c == '[') {
					nextStart = i + 1;
					numLeft = 1;
					inBracket = true;
				} else { // It must be a normal character
					sb.append(c);
				}
			} else { // In a bracket mode
				if(c == '[') ++numLeft;
				if (c == ']') {
					if (--numLeft == 0) {
						if(i > nextStart){
							String temp = decode(s, nextStart, i);
							while (dup > 0) {
								sb.append(temp);
								--dup;
							}
						}
						inBracket = false;
					}
				}
			}
		}
		return sb.toString();
	}

	private boolean isDigit(char c) {
		int i = (int) (c - '0');
		return (i >= 0 && i < 10);
	}

}