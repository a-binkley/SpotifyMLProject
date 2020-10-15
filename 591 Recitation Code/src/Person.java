
public class Person {
	//Instance variables
	String name;
	String nationality;
	int age;
	
	@Override // This tells Java to ignore the inherited method, so we can define our own!
	public String toString() {
		return(name + " " + nationality + " " + age);
	}
	
	public static void main(String[] args) {
		Person x = new Person();
		Person y = new Person();
		Person z = new Person();
		x.age = 17;
		y.age = 25;
		z.age = 58;
		x.nationality = "USA";
		y.nationality = "Belgium";
		z.nationality = "Switzerland";
		
		ElectionOffice eo_1 = new ElectionOffice();
		
		System.out.println("Can they vote? " + eo_1.canTheyVote(x) + " " + eo_1.canTheyVote(y) + " " + eo_1.canTheyVote(z));
		System.out.println(x.toString());
	}
}
