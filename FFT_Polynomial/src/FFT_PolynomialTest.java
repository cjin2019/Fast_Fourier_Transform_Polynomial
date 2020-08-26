import static org.junit.Assert.*;

import org.junit.Test;

public class FFT_PolynomialTest {
	
	@Test
	public void testMultiply1() {
		int[] A = new int[] {1};
		int[] B = new int[] {1};
		int[] Cexp = new int[] {1};
		
		FFT_Polynomial fftp = new FFT_Polynomial();
		int[] Cres = fftp.multiply(A, B);
		
		assertArrayEquals(Cexp, Cres);
	}
	
	@Test
	public void testMultiplyLin() {
		int[] A = new int[] {2, 5};
		int[] B = new int[] {1, 1};
		int[] Cexp = new int[] {2, 7, 5};
		
		FFT_Polynomial fftp = new FFT_Polynomial();
		int[] Cres = fftp.multiply(A, B);
		
		assertArrayEquals(Cexp, Cres);
	}
	
	@Test
	public void testMultiplyQuad() {
		int[] A = new int[] {1, 3, 2};
		int[] B = new int[] {3, 4, 7};
		int[] Cexp = new int[] {3, 13, 25, 29, 14};
		
		FFT_Polynomial fftp = new FFT_Polynomial();
		int[] Cres = fftp.multiply(A, B);
		
		assertArrayEquals(Cexp, Cres);
		
	}

}
