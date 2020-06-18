package database;

import model.Award;
import model.Nominee;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class handles all database related transactions
 */
public class AwardDatabaseHandler {
    // Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public AwardDatabaseHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void insertAward(Award model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO award VALUES (?,?,?,?)");
            ps.setInt(1, model.getAID());
            ps.setString(2, model.getName());
            ps.setString(3, model.getStartDate());
            ps.setString(4, model.getEndDate());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertNominee(Nominee model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO nominee VALUES (?,?,?,?)");
            ps.setInt(1, model.getNomID());
            ps.setInt(2, model.getVoteCount());
            ps.setInt(3, model.getID());
            ps.setInt(4, model.getAwardID());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteAward(int aID) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM award WHERE aID = ?");
            ps.setInt(1, aID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Award " + aID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateAward(int aID, String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE award SET name = ? WHERE aID = ?");
            ps.setString(1, name);
            ps.setInt(2, aID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Award " + aID + " does not exist!");
            }
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void voteNominee(int nom_id) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE nominee SET vote_count = vote_count + 1 WHERE nom_id = ?");
            ps.setInt(1, nom_id);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Award " + nom_id + " does not exist!");
            }
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public Integer getNomineeVotes(int nom_id) {
        int votes = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT vote_count from nominee where nom_id = " + nom_id);

            while (rs.next()) {
                votes = rs.getInt("vote_count");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return votes;
    }

    // select * from ... where CONDs
    // public void selectAward(List<String> fields) {
    public void selectAward(String aid) {
        try {
            PreparedStatement ps = connection.prepareStatement("Select * from award WHERE aID = ?");
            ps.setInt(1, Integer.valueOf(aid));

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Award with id:" + aid + " does not exist!");
            }
            // ps.
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public List<String[]> projectAward(List<String> fields) {
        System.out.println("start");
        ArrayList<String[]> res = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            StringBuffer statement = new StringBuffer("SELECT");
            for (String f : fields) statement.append(" " + f + ",");
            statement.setLength(statement.length() - 1);
            statement.append(" FROM award");
            
            System.out.println(statement.toString());
            ResultSet rs = st.executeQuery(statement.toString());
            while(rs.next()) {
                String[] cur = new String[fields.size()];
                for (int i = 1; i <= cur.length; i++) {
                    cur[i - 1] = rs.getString(i);
                    System.out.println(cur[i - 1] + " ");
                }
                res.add(cur);
            }
            connection.commit();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return res;
    }

    // inner join
    public List<String[]> joinAward(List<String> f1, List<String> f2, String t1, String t2, String c1, String c2) {
        System.out.println("start");
        ArrayList<String[]> res = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            StringBuffer statement = new StringBuffer("SELECT");
            for (String f : f1) statement.append(" a." + f + ",");
            for (String f : f2) statement.append(" b." + f + ",");
            statement.setLength(statement.length() - 1);

            statement.append(" FROM ");
            statement.append(t1 + " a, ");
            statement.append(t2 + " b ");
            statement.append(" WHERE ");
            statement.append(" a." + c1);
            statement.append(" = b." + c2);

            System.out.println(statement.toString());
            ResultSet rs = st.executeQuery(statement.toString());
            System.out.println(1);
            System.out.println(rs.toString());
            while(rs.next()) {
                String[] cur = new String[f1.size() + f2.size()];
                for (int i = 1; i <= cur.length; i++) {
                    System.out.println(4);
                    cur[i - 1] = rs.getString(i);
                    System.out.println(cur[i - 1]);
                    System.out.println(cur[i - 1] + " ");
                }
                res.add(cur);
            }
//            int rowCount = ps.executeUpdate();
//            if (rowCount == 0) {
//                System.out.println(WARNING_TAG + " Award does not exist!");
//            }
            connection.commit();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return res;
    }

    public Award[] getAwardInfo() {
        ArrayList<Award> result = new ArrayList<Award>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM award");

            while (rs.next()) {
                Award model = new Award(rs.getInt("aID"),
                        rs.getString("startDate"),
                        rs.getString("endDate"),
                        rs.getString("name"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Award[result.size()]);
    }

    public Nominee[] getNomineeInfo() {
        ArrayList<Nominee> result = new ArrayList<Nominee>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM nominee");

            while (rs.next()) {
                Nominee model = new Nominee(rs.getInt("nom_id"),
                        rs.getInt("vote_count"),
                        rs.getInt("id"),
                        rs.getInt("award_id"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Nominee[result.size()]);
    }

    public boolean login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

    private void rollbackConnection() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void awarddatabaseSetup() {
        dropAwardTableIfExists();
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(
                    "CREATE TABLE award (aID integer PRIMARY KEY, " +
                    "startdate varchar2(10), enddate varchar2(10), " + "name varchar2(20) not null)");
            stmt.executeUpdate("CREATE TABLE moviestaff (id integer PRIMARY KEY, " +
                            "staffname varchar2(20), dob varchar2(10), role varchar2(20))");
            stmt.executeUpdate("CREATE TABLE nominee (nom_id integer PRIMARY KEY, " +
                    "vote_count integer, id integer, award_id integer, foreign key (id) references moviestaff(id), " +
                            "foreign key (award_id) references award(aID))");
            stmt.executeUpdate("INSERT INTO award VALUES (1, '08/12/2019', '08/14/2020', 'The Award')");
            stmt.executeUpdate("INSERT INTO award VALUES (12, '08/11/2019', '08/15/2020', 'The Award 2')");
            stmt.executeUpdate("INSERT INTO moviestaff VALUES (19285746, 'Daniel', '08/14/2020', 'Actor')");
            stmt.executeUpdate("INSERT INTO moviestaff VALUES (12345678, 'Ivan', '08/24/2020', 'Actor')");
            stmt.executeUpdate("INSERT INTO moviestaff VALUES (15678349, 'David', '08/04/2010', 'Director')");
            stmt.executeUpdate("INSERT INTO moviestaff VALUES (01928347, 'Felicia', '08/12/2020', 'Director')");
            stmt.executeUpdate("INSERT INTO moviestaff VALUES (57392047, 'Andy', '08/11/2020', 'Actor')");
            stmt.executeUpdate("INSERT INTO moviestaff VALUES (02937586, 'Daria', '08/31/2020', 'Actor')");
            stmt.executeUpdate("INSERT INTO nominee VALUES (0, 0, 19285746, 1)");
            stmt.executeUpdate("INSERT INTO nominee VALUES (1, 6, 12345678, 1)");
            stmt.executeUpdate("INSERT INTO nominee VALUES (2, 0, 01928347, 12)");
            stmt.executeUpdate("INSERT INTO nominee VALUES (3, 6, 12345678, 12)");
            System.out.println("Occurred");
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public String findStaffIds(String role) {
        ArrayList<String> result = new ArrayList<String>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM moviestaff where role = " + role);

            while (rs.next()) {
                result.add(Integer.toString(rs.getInt("id")));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return Arrays.toString(result.toArray(new String[result.size()]));
    }

    public String findTotalVotes() {
        ArrayList<String> result = new ArrayList<String>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT sum(vote_count), award_id FROM nominee group by award_id");

            while (rs.next()) {
                result.add("(" + rs.getInt("sum(vote_count)") + ", " + rs.getInt("award_id") + ")");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return Arrays.toString(result.toArray(new String[result.size()]));
    }

    public String findWinningNom(int award_id) {
        String winningnom = null;

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id from nominee where vote_count = " +
                    "(select max(vote_count) from nominee where award_id = " + award_id + ")");

            while (rs.next()) {
                winningnom = rs.getString("id");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return winningnom;
    }

    public String findAllNomedStaff() {
        ArrayList<String> result = new ArrayList<String>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select award_id, id from nominee n " +
                    "where not exists "
                    + "((select award_id from award) " +
                    "minus (select np.award_id from nominee np where np.id = n.id))"
            );

            while (rs.next()) {
                result.add(Integer.toString(rs.getInt("id")));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return Arrays.toString(result.toArray(new String[result.size()]));
    }

    private void dropAwardTableIfExists() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select table_name from user_tables");

            while(rs.next()) {
                if(rs.getString(1).toLowerCase().equals("award")) {
                    stmt.execute("DROP TABLE nominee");
                    stmt.execute("DROP TABLE award");
                    stmt.execute("DROP TABLE moviestaff");
                    break;
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }
}
