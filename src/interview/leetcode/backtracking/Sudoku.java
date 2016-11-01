package interview.leetcode.backtracking;
import java.util.Arrays;

public class Sudoku {
	
	private int[] row;
	private int[] col;
	private int[] grid;
	private int[] miss;
	public long numSol = 0;
	
	public static void main(String[] args){
		/*
		int num = 0x15a2;
		System.out.println(Integer.toBinaryString(num));
		int one = 0;
		while(num != 0){
			one = Integer.lowestOneBit(num);
			System.out.println(Integer.toBinaryString(one));
			System.out.println(1 + Integer.numberOfTrailingZeros(one));
			num -= one;
		}
		*/
		
		/*
		char[][] matrix = {
			{'5', '3', '.', '.', '7', '.', '.', '.', '.'},
			{'6', '.', '.', '1', '9', '5', '.', '.', '.'},
			{'.', '9', '8', '.', '.', '.', '.', '6', '.'},
			{'8', '.', '.', '.', '6', '.', '.', '.', '3'},
			{'4', '.', '.', '8', '.', '3', '.', '.', '1'},
			{'7', '.', '.', '.', '2', '.', '.', '.', '6'},
			{'.', '6', '.', '.', '.', '.', '2', '8', '.'},
			{'.', '.', '.', '4', '1', '9', '.', '.', '5'},
			{'.', '.', '.', '.', '8', '.', '.', '7', '9'}
		};
		*/
		
		

		char[][] matrix = {
				{'1', '2', '3', '4', '5', '6', '7', '8', '9'},
				{'.', '.', '.', '.', '.', '.', '.', '.', '.'},
				{'.', '.', '.', '.', '.', '.', '.', '.', '.'},
				{'.', '.', '.', '.', '.', '.', '.', '.', '.'},
				{'.', '.', '.', '.', '.', '.', '.', '.', '.'},
				{'.', '.', '.', '.', '.', '.', '.', '.', '.'},
				{'.', '.', '.', '.', '.', '.', '.', '.', '.'},
				{'.', '.', '.', '.', '.', '.', '.', '.', '.'},
				{'.', '.', '.', '.', '.', '.', '.', '.', '.'}
			};
			
		//printMatrix(matrix);
		//System.out.println();
		Sudoku sudo = new Sudoku();
		sudo.solveSudoku(matrix);
		//printMatrix(matrix);
		System.out.println("# of solutions: " + sudo.numSol);
		
	}
	
	public static void printMatrix(char[][] matrix){
		int nCol = matrix[0].length;
		for(int i = 0; i < matrix.length; ++i){
			Character[] temp = new Character[nCol];
			for(int j = 0; j < nCol; ++j){
				temp[j] = matrix[i][j];
			}			
			System.out.println(String.valueOf(Arrays.asList(temp)));
		}
	}
	
	public void solveSudoku(char[][] matrix){
		if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
			return;
		}
		int N = matrix.length;
		row = new int[N];
		col = new int[N];
		grid = new int[N];
		Arrays.fill(row, 0x1ff);
		Arrays.fill(col, 0x1ff);
		Arrays.fill(grid, 0x1ff);
		int count = 0;
		miss = new int[N * N];
		
		char cur = ' ';
		for(int i = 0; i < N; ++i){
			for(int j = 0; j < N; ++j){
				cur = matrix[i][j];
				if(cur == '.'){
					miss[count++] = oneD(i, j);
				}
				else{
					int num = getNum(cur);
					use(i, row, num);
					use(j, col, num);
					use(grid(i, j), grid, num);
				}
			}
		}	
		search(matrix, count, 0);
	}
	
	public boolean search(char[][] matrix, int count, int level){
		//if(level == count) return true;
		if(level == count){
			//++numSol;
			if(++numSol % 1000000 == 0){
				System.out.println("Found " + numSol + " solutions");
			}
			return true;
		}
		int t = miss[level];
		int i = t / 9, j = t % 9;
		
		int avail = row[i] & col[j] & grid[grid(i, j)];
		int one = 0, num = 0;
		boolean res = false;
		while(avail != 0){
			one = Integer.lowestOneBit(avail);
			num = Integer.numberOfTrailingZeros(one) + 1;
			avail -= one;
			matrix[i][j] = getChar(num);
			use(i, row, num);
			use(j, col, num);
			use(grid(i, j), grid, num);
			//res = search(matrix, count, level + 1);
			//if(res) return res;
			res = search(matrix, count, level + 1);
			// Recover the state
			unuse(i, row, num);
			unuse(j, col, num);
			unuse(grid(i, j), grid, num);
			//matrix[i][j] = '.';
		}
		return res;
	}
	
	public static char getChar(int x){
		return (char) ((int)'0' + x);
	}
	
	public static int getNum(char c){
		return c - '0';
	}
	
	public static int oneD(int i, int j){
		return i * 9 + j;
	}
	
	public static int[] twoD(int t){
		int[] pos = new int[2];
		pos[0] = t / 9;
		pos[1] = t % 9;
		return pos;
	}
	
	public static int grid(int i, int j){
		int gRow = i / 3;
		int gCol = j / 3;
		return gRow * 3 + gCol;
	}
	
	/**
	 * Set the array[i] to be used by num
	 * @param i
	 * @param array
	 * @param num
	 */
	public static void use(int i, int[] array, int num){
		int mask = (1 << (num - 1));
		mask ^= -1;
		array[i] &= mask;
	}
	
	public static void unuse(int i, int[] array, int num){
		int mask = (1 << (num - 1));
		array[i] |= mask;
	}
}