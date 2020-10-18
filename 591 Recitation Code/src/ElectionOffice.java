public class ElectionOffice {
	String name;

	
	public boolean canTheyVote(Person p) {
		if (p.age >= 18 && (p.nationality.equals("USA") || p.nationality.equals("Albania") || p.nationality.equals("Belgium") || p.nationality.equals("Madagascar"))) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {

	}

}
