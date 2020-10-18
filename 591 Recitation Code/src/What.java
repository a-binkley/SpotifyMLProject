public class What {
	String name;
	int age;

	void messAge(int x) {
		x = x + 1;
	}

	void messAge2() {
		age = age / 2;
	}

	public static void main(String[] args) {
		What what2 = new What();
		What what3 = what2;
		what3.age = 7;
		System.out.println("what2 has an age that is " + what2.age);
	}
}