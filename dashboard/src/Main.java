import net.Connection;
import data.DataUsers;

public class Main {
	public static void main(String[] args) {
		DataUsers du = new DataUsers();
		Connection connection = new Connection();

		if (connection.connect("Dupond", "Dupont"))
			System.out.println(connection.getUser());
	}
}
