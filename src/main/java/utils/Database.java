package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.sql.*;

public class Database {
    String JDBC_DRIVER="com.mysql.jdbc.Driver";
    String DBUrl=null;
    String Usrname=null;
    String Pwd=null;

    private final static Logger LOGGER= LoggerFactory.getLogger(Database.class.getName());

    public void initiatDb()
    {
//        DBUrl = getProperties().get("stageDbUrl");
//        Usrname = getProperties().get("DbUserForStage");
//        Pwd = getProperties().get("DbPwdForStage");


    }

    public String GetResultQueryExecutor(String dbName, String queryStatement) throws Exception {
        //ResultSet resVal = null;
        Connection conn = null;
        Statement stmt = null;
        String value = null;

        int flag = 1;

        initiatDb();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DBUrl + dbName, Usrname, Pwd);

            stmt = conn.createStatement();
            String sql = queryStatement;

            ResultSet rs = stmt.executeQuery(sql);

            if (rs != null) {
                rs.beforeFirst();
                rs.last();
                rs.getRow();
                value = rs.getString(1);
            }
            System.out.println("value:" + value);
        }catch (SQLException sq){
            flag =0;
            sq.printStackTrace();
            System.out.println("this query failed"+queryStatement);

        }catch(Exception e ){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException sq){
                sq.printStackTrace();
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException sq){
                sq.printStackTrace();
            }
            Assert.assertTrue(flag == 1, "Error!! Query execution failed...");
        }

        return value;
    }

}
