import net.Connection;
import data.DataUsers;


public class Main {
	public static void main(String[] args) {
		DataUsers du = new DataUsers();
		Connection connection = new Connection();

		du.newUser("Dupond", "Dupont");
		du.newUser("admin", "admin");
		
		du.addProjet("Dupond", "https://github.com/Celdin/dashboard.git");
		
	}
}
