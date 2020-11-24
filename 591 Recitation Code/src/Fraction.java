
public class Fraction {
	public int numerator;
	public int denominator;
	
	public Fraction(int num, int denom) {
		numerator = num;
		denominator = denom;
	}
	
	public Fraction simplify(int num, int denom) {
		while (true) {
			if (num % 5 == 0 && denom % 5 == 0) {
				num /= 5;
				denom /= 5;
				continue;
			}
			if (num % 3 == 0 && denom % 3 == 0) {
				num /= 3;
				denom /= 3;
				continue;
			}
			if (num % 2 == 0 && denom % 2 == 0) {
				num /= 2;
				denom /= 2;
				continue;
			}
			break;
		}
		Fraction out = new Fraction (num, denom);
		return out;
	}
	
	public Fraction addFraction(Fraction other) {
		this.denominator *= other.denominator;
		this.numerator *= other.denominator;
		other.numerator *= this.denominator;
		return simplify(this.numerator + other.numerator, this.denominator);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
