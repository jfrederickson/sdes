/**
 * An implementation of Simplified DES, or SDES
 * @author Jonathan Frederickson
 * TODO: Comment things better
 */

package sdes;

import java.util.Arrays;
import java.lang.IllegalArgumentException;
import java.util.Scanner;
import java.lang.StringBuilder;

public class SDES {
	/**
	 * Extract a permutation of the input array based on a 
	 * permutation vector "epv".
	 * @param in
	 * @param epv
	 * @return
	 */
	public static boolean[] expPerm(boolean[] in, int[] epv) {
		boolean[] result = new boolean[epv.length];
		for(int i=0; i<epv.length; i++) {
			result[i] = in[epv[i]];
		}
		return result;
	}
	/**
	 * Gets a 10-digit key from a scanner.
	 * TODO: Further testing - I'm not very familiar with scanners
	 * @param s the scanner to read from
	 * @return a String containing the values read from the scanner 
	 */
	public static boolean[] getKey10(Scanner s) {
		boolean[] result = new boolean[10];
		String keyString = s.nextLine();
		
		if(keyString.length() != 10) {
			System.err.println("Error: must input 10-bit key");
			return null;
		}
		
		for(int i=0; i<10; i++) {
			switch(keyString.charAt(i)) {
				case '1': result[i] = true;
				break;
				case '0': result[i] = false;
				break;
				default: System.err.println("Invalid digit");
				break;
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param bytes
	 * @return
	 */
	public static String byteArrayToString(byte[] bytes) {
		StringBuilder build = new StringBuilder();
		for(byte b : bytes) {
			build.append(b);
		}
		return build.toString();
	}
	
	/**
	 * Extract only left half of an array
	 * @param in
	 * @return
	 */
	public static boolean[] lh(boolean[] in) {
		int midpoint = in.length / 2;
		boolean[] result;
		
		// Compensate for the possibility of odd array lengths
		if(in.length % 2 == 1) {
			 result = new boolean[in.length-(midpoint+1)];
		}
		else result = new boolean[in.length - midpoint];
		for(int i = 0; i < result.length; i++) {
			result[i] = in[i];
		}
		return result;
	}
	
	/**
	 * Extract only right half of an array
	 * @param in
	 * @return
	 */
	public static boolean[] rh(boolean[] in) {
		int midpoint = in.length / 2;
		boolean[] result = new boolean[in.length - midpoint];
		int arrayCount = 0;
		for(int i = midpoint; i < in.length; i++) {
			result[arrayCount] = in[i];
			arrayCount++;
		}
		return result;
	}
	
	/**
	 * Exclusive OR function.
	 * Example: 101 XOR 110 == 011
	 * TODO: Put a truth table here maybe?
	 * @param a1
	 * @param a2
	 * @return a1 XOR a2
	 */
	public static boolean[] xor(boolean[] a1, boolean[] a2) {
		if(a1.length != a2.length) return null;
		boolean[] result = new boolean[a1.length];
		for(int i=0; i<a1.length; i++) {
			result[i] = a1[i] ^ a2[i];
		}
		return result;
	}
	
	public static boolean[] concat(boolean[] a1, boolean[] a2) {
		boolean[] result = new boolean[a1.length + a2.length];
		for(int i=0; i<a1.length; i++) {
			result[i] = a1[i];
		}
		for(int j=a1.length; j<result.length; j++) {
			result[j] = a2[j];
		}
		return result;
	}
	
	/**
	 * Converts a bit array (an array of booleans) into a byte
	 * @param bits an array of boolean variables representing bits
	 * @return the parameter in byte form
	 */
	public static byte bitArrayToByte(boolean[] bits) {
		if(bits.length > 8) throw new IllegalArgumentException();
		
		// Loop starting from the LSB
		byte result = 0;
		int i = bits.length-1;
		byte mask = (byte) 0b00000001;
		while(i >= 0) {
			// If the bit at this position is true, logical AND the mask with
			// the result. The mask will have a bit at the same position as
			// the current array position.
			if(bits[i]) {
				// If we were right-shifting anywhere, we would need to 
				// AND with 0xFF to avoid sign weirdness
				result = (byte) (result | mask);
			}
			
			// Shift the mask left, decrement array position
			mask = (byte) (mask << 1);
			i--;
		}
		return result;
	}
	
	/**
	 * Converts a byte into a bit array (array of booleans)
	 * @param b
	 * @return
	 */
	public static boolean[] byteToBitArray(byte b) {
		// This pretty much works like bitArrayToByte above
		// TODO: Comment this better
		boolean[] result = new boolean[8];
		byte mask = (byte) 0b00000001;
		for(int i = 7; i>=0; i--) {
			if((byte) (b & mask) != 0) {
				result[i] = true;
			}
			else result[i] = false;
			mask = (byte) (mask << 1);
		}
		return result;
	}
	
	public static void main(String[] args) {
		boolean[] test = new boolean[] {true, true, true, false, true};
		System.out.println(Arrays.toString(test));
		byte b = bitArrayToByte(test);
		System.out.println(Integer.toBinaryString(bitArrayToByte(test)));
		System.out.println(Arrays.toString(byteToBitArray(b)));
		System.out.println(Arrays.toString(expPerm(test, new int[] {1, 3, 3, 3})));
		Scanner sc = new Scanner("1010101010");
		getKey10(sc);
	}
}
