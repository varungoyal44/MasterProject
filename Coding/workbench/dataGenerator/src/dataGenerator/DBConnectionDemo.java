package dataGenerator;

public class DBConnectionDemo {

	public static void main(String[] args) throws Exception {
		MySQLAccess dao = new MySQLAccess();
		dao.readDataBase();
	}

}