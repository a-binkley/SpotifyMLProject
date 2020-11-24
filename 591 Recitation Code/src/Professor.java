/**
 * Professor inherits from Teacher
 * @author adria
 *
 */
public class Professor extends Teacher {
	private String university;
	
	public static void main(String[] args) {
		Professor p = new Professor();
		p.setName("Arvind");
		p.teach();
	}
}
