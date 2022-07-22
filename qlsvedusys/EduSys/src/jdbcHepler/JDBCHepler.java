/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbcHepler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Quyết Chiến
 */
public class JDBCHepler {
    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String url = "jdbc:sqlserver://localhost:1433;databaseName=EduSys";
    private static String user = "sa";
    private static String passWord = "123";
    public static Connection ketnoi(){
        try {
            Class.forName(driver);
            Connection cn = DriverManager.getConnection(url, user, passWord);
            return cn;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }
    
    public static PreparedStatement getStmt(String sql, Object... args) throws SQLException{
        Connection connection = DriverManager.getConnection(url,user,passWord);
        PreparedStatement pstm = null;
        if (sql.trim().startsWith("{")) {
            pstm = connection.prepareCall(sql);
            
        }else{
            pstm = connection.prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            pstm.setObject(i+1, args[i]);
        }
        return pstm;
    }
    
    public static int update(String sql,Object...args){
        try {
            PreparedStatement stmt = getStmt(sql, args);
            try {
                return  stmt.executeUpdate();
                
            } finally {
                stmt.getConnection().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public  static ResultSet query(String sql, Object... args){
        try {
            PreparedStatement stmt = getStmt(sql, args);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public static Object value(String sql, Object... args){
        try {
            ResultSet rs = query(sql, args);
            if (rs.next()) {
                return rs.getObject(0);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }
}
