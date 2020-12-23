package com.example.test_log.data;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBLogin implements Runnable{
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/supplier_parts?useSSL=false&serverTimezone=UTC";
    private static final String user = "root";
    private static final String pwd = "root";

    private static Connection conn=null;
    private static int jumper=111;

//
    private static String Username;
    private static String Password;
//

    static void linkMysql(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("驱动加载成功！");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            conn=DriverManager.getConnection(url, user, pwd);
            System.out.println("连接数据库成功！");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void linkLoginsql(String username, String password) {
        try {
            linkMysql();
            String logSql = "Select * from t_user where username='"+ username+"'and password='"+ password+ "'";
            Log.i("sql", logSql);
            PreparedStatement stmt = conn.prepareStatement(logSql);
//            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(logSql);

            // 获取跳转判断
            if(rs.next()){
                Log.i("jumper:", String.valueOf(jumper));
                jumper=233;
                Username = rs.getString("username");
                Password=rs.getString("password");
            }else{
                jumper=777;
            }
            Log.i("jumper:", String.valueOf(jumper));

            rs.close();
            stmt.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }

        //关闭数据库
        if(conn!=null){
            try {

                conn.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //传递跳转判断
    public static int getjumper(){
        return jumper;
    }
    //传递用户ID
    public static String getusername(){
        return Username;
    }
    //传递用户昵称
    public static String getpassword(){
        return Password;
    }

    @Override
    public void run() {

    }
}

