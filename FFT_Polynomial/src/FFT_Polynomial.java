import java.util.*;

public class FFT_Polynomial {
	
	/*
	 * @ params A, B contain the coefficients of each term
	 * A[i] = a_i where a_i is the coefficient for the term a_i*x^i
	 * Assumption: A and B are of the same length
	 * @ returns the coefficient of the product of polynomial A and B
	 */
	
	public int[] multiply(int[] A, int[] B) {
		//sets up the list of roots of unity
		List<ComplexNumber> w = new ArrayList<ComplexNumber>();
		int m = 2*A.length-1;
		for(int j = 0; j < m; j++) {
			w.add(new ComplexNumber(2 * Math.PI * j / m));
		}
		
		//converts the int arrays to complex numbers for computation
		ComplexNumber[] Ac = new ComplexNumber[A.length];
		ComplexNumber[] Bc = new ComplexNumber[B.length];
		
		for(int ci = 0; ci < A.length; ci++) {
			Ac[ci] = new ComplexNumber(A[ci], 0);
			Bc[ci] = new ComplexNumber(B[ci], 0);
		}
		
		return helperMultiply(Ac, Bc, w);
	}
	
	/*
	 * @ params A and B are arrays containing the coefficients of the polynomial
	 * @ param w is a list containing the roots of the unity
	 * @ returns the coefficient of the product
	 */
	public int[] helperMultiply(ComplexNumber[] A, ComplexNumber[] B, List<ComplexNumber> w) {
		
		//computes A(w) and B(w) in O(n log n) where |A| = |B| = O(n)
		ComplexNumber[] Aw = FFT(A, w);
		ComplexNumber[] Bw = FFT(B, w);
		//Computes C(w) = A(w)B(w) in O(n)
		ComplexNumber[] Cw = new ComplexNumber[Aw.length];
		for(int wi = 0; wi < Aw.length; wi++) {
			Cw[wi] = Aw[wi].times(Bw[wi]);
		}
		//Computes the inverse of the roots of unity
		List<ComplexNumber> w_inv = new ArrayList<ComplexNumber>();
		for(ComplexNumber wi: w) {
			w_inv.add(wi.inverse());
		}
		//Computes the coefficients from C(w) 
		ComplexNumber[] C_Complex = FFT(Cw, w_inv); 
		int[] C = new int[C_Complex.length];
		
		//Converts back to integer coefficients
		for(int ci = 0; ci < C.length; ci++) {
			C[ci] = (int) Math.round(C_Complex[ci].getReal()/C.length);
		}
		
		return C;
	}
	
	/*
	 * @ params A is an array containing the coefficients
	 * @ param w is a list containing the roots of unity
	 * @ returns an array computing A(w0),... A(wn-1)
	 */
	public ComplexNumber[] FFT(ComplexNumber[] A, List<ComplexNumber> w) {
		//if A is length 1, it contains only a constant. 
		if(A.length==1) {
			ComplexNumber[] Aw = new ComplexNumber[w.size()];
			for(int ai = 0; ai < Aw.length; ai++) {
				Aw[ai] = new ComplexNumber(A[0].getReal(), A[0].getImag());
			}
			return Aw;
		}
		else {
			//splits A into even termed and odd-termed coefficients
			ComplexNumber[] A_even = new ComplexNumber[A.length/2 + A.length%2];
			ComplexNumber[] A_odd = new ComplexNumber[A.length/2];
			
			Set<Integer> visited = new HashSet<Integer>();
			List<ComplexNumber> w_square = new ArrayList<ComplexNumber>();
			for(int wi = 0; wi < w.size(); wi++) {
				int square_index = (2*wi)%w.size();
				if(!visited.contains(square_index)){
					visited.add(square_index);
					w_square.add(w.get(square_index));
				}
			}
	
			//get the A_even and Aodd
			for(int ai = 0; ai < A.length; ai++) {
				if(ai%2==0) {
					A_even[ai/2] = A[ai]; 
				}
				else {
					A_odd[ai/2] = A[ai];
				}
			}
			
			//computes A_even and A_odd
			ComplexNumber[] A_even_w2 = FFT(A_even, w_square);
			ComplexNumber[] A_odd_w2 = FFT(A_odd, w_square);
			
			//Combines computation to get our desired result
			ComplexNumber[] Aw = new ComplexNumber[w.size()];
			for(int wi = 0; wi < Aw.length; wi++) {
				int w2i = wi;
				if(w2i >= w_square.size()) {
					w2i -= w_square.size();
				}
				Aw[wi] = A_even_w2[w2i].add(A_odd_w2[w2i].times(w.get(wi)));
			}
			
			
			return Aw;
		}
	}

}
