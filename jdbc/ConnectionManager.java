package jdbc;

import annotation.DAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Map;
import java.util.Properties;

@DAO
public class ConnectionManager {
	public static Connection getConnection() {
		loadEnvironmentVariables();
		String url = "jdbc:mysql://localhost:3306/nemne_insurance?serverTimezone=UTC&useSSL=false";
		String id = System.getProperty("DB_ID");
		String pw = System.getProperty("DB_PASSWORD");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(url, id, pw);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	private static void loadEnvironmentVariables() {
		try {
			Path currentPath = Paths.get("");
			String absolutePath = currentPath.toAbsolutePath().toString();
			FileInputStream fileInputStream = new FileInputStream(absolutePath + "/.env");
			Properties properties = new Properties();
			properties.load(fileInputStream);

			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				if(!value.equals("")) System.setProperty(key, value);
			}

			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
