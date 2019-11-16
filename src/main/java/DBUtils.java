import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {

    private String url;
    private String user;
    private String password;
    private int rowCount;
    private XMLFileWriter xmlFileWriter = new XMLFileWriter();

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public void connectToDB() {
        try (Connection connect = DriverManager.getConnection(url, user, password)) {
            List<String> fieldList = new ArrayList<>();
            PreparedStatement clearTable = connect.prepareStatement("truncate table test");
            clearTable.execute();

            PreparedStatement insertEntry = connect.prepareStatement("insert into test values (?)");
            for (int i = 1; i <= rowCount; i++) {
                insertEntry.setInt(1, i);
                insertEntry.addBatch();
            }
            int[] batch = insertEntry.executeBatch();

            PreparedStatement getEntries = connect.prepareStatement("select * from test");
            ResultSet result = getEntries.executeQuery();
            while (result.next()) {
                fieldList.add(result.getString("field"));
            }

            xmlFileWriter.writeXML(fieldList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
