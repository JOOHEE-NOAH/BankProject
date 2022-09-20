package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Jdbc {
   private static Jdbc instance;
   private Connection conn;
   public Jdbc() {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver"); //드라이브로딩 
         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","java");
                                          //"jdbc:oracle:thin:@localhost:1521:xe","hr","hr"
         
      } catch (ClassNotFoundException | SQLException e) {
         e.printStackTrace();
      }
   }
   public static Jdbc getInstance() {
      if(instance == null )
         instance = new Jdbc();
      return instance;
   }
   public Connection getConnection() {
      return this.conn;
   }
}

