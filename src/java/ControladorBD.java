import java.sql.*;
public class ControladorBD {
    String driver="com.mysql.jdbc.Driver";
    String url="jdbc:mysql://localhost:3306";
    Connection con=null;
    Statement stmt;
    ControladorBD(){
        try{
                Class.forName(driver);

                con=DriverManager.getConnection(url,"root","");
                stmt=con.createStatement();

                ejecutarUpdate("CREATE DATABASE IF NOT EXISTS FormularioJava");
                ejecutarSentencia("USE FormularioJava");
                ejecutarUpdate("CREATE TABLE IF NOT EXISTS usuarios("
                        + "id int auto_increment primary key,"
                        + "nombre varchar(20),"
                        + "contrasena varchar(80),"
                        + "email varchar(50),"
                        + "telefono int,"
                        + "edad int)");
                ejecutarUpdate("INSERT INTO usuarios IF NOT EXISTS(0,null,null,0,0");
            
        }catch(ClassNotFoundException e){
            System.out.println("Controlador JDBC no encontrado: "+e.toString());
        }catch(SQLException e){
            System.out.println("Excepcion capturada de SQL: "+e.toString());
        }
    }
    public final ResultSet ejecutarSentencia(String s){
        try{
            ResultSet query= stmt.executeQuery(s);
            return query;
        }catch(SQLException sqle){
            return null;}
    }
    public final String ejecutarUpdate(String s){
        try{
            return stmt.executeUpdate(s)+"";
        }catch(SQLException sqle){return "Excepcion capturada de SQL: "+sqle.toString();}
    }
    public void mandarDatos(){
        
    }
    public void cerrarConexión(){
        try{
            if(con!=null)
                con.close();
        }catch(SQLException e){
            System.out.println("No se puede cerrar la conexion: "+e.toString());
        }
    }
}
