import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PropertyImageAdder {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/project";
        String username = "root";
        String password = "13Lock02";

        String imagePath = "/Users/lockyer/Desktop/image.jpg";

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            Connection conn = DriverManager.getConnection(url, username, password);

            // Prepare the SQL statement to update the "image" column where the value is null
            PreparedStatement stmt = conn.prepareStatement("UPDATE property SET image = ? ");

            // Read the image file into a byte array
            File imageFile = new File(imagePath);
            byte[] imageData = new byte[(int) imageFile.length()];
            FileInputStream fis = new FileInputStream(imageFile);
            fis.read(imageData);
            fis.close();

            // Set the image data as a BLOB parameter in the SQL statement
            stmt.setBytes(1, imageData);

            // Execute the SQL statement
            int rowsUpdated = stmt.executeUpdate();

            System.out.println(rowsUpdated + " rows updated.");

            // Close the statement and connection
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
