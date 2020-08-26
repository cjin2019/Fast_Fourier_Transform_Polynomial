
public class ComplexNumber {
	private final double real;
	private final double imag;
	
	public ComplexNumber(double pReal, double pimag) {
		real = pReal;
		imag = pimag;
	}
	
	public ComplexNumber(double pRadianAngle) {
		real = Math.cos(pRadianAngle);
		imag = Math.sin(pRadianAngle);
	}
	
	public double getReal() {
		return real;
	}
	
	public double getImag() {
		return imag;
	}
	
	public ComplexNumber add(ComplexNumber b) {
		return new ComplexNumber(real + b.real, imag + b.imag);
	}
	
	public ComplexNumber times(ComplexNumber b) {
		return new ComplexNumber(real * b.real - imag * b.imag, real * b.imag + b.real * imag );
	}
	
	public ComplexNumber scale(double factor) {
		return new ComplexNumber(real * factor, imag * factor);
	}
	
	public ComplexNumber inverse() {
		return new ComplexNumber(real, -1* imag);
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof ComplexNumber)) {
			return false;
		}
		else {
			ComplexNumber c = (ComplexNumber) o;
			return c.real == real && c.imag == imag;
		}
	}
}
