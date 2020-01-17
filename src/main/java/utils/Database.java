package utils;

import org.testng.Assert;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Database {
    public String JDBC_DRIVER="com.mysql.jdbc.Driver";
    public String DBUrl=null;
    public String Usrname=null;
    public String Pwd=null;
    public Properties properties;


    public void initiatDb()
    {
        properties = new Properties();
        try {
            properties.load(new FileReader("src/main/resources/config.properties"));
            DBUrl = properties.getProperty("stageDbUrl");
            Usrname = properties.getProperty("DbUserForStage");
            Pwd = properties.getProperty("DbPwdForStage");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String GetResultQueryExecutor(String dbName, String queryStatement,String column)  {
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
                value = rs.getString(column);
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
