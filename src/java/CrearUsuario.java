import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.security.MessageDigest;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;


public class CrearUsuario extends HttpServlet{
    protected ControladorBD bd;
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String nom = request.getParameter("nombre")+"";
        String email = request.getParameter("email")+"";
        String con = request.getParameter("con")+"";
        String con1 = request.getParameter("con1")+"";
        String tel = request.getParameter("tel")+"";
        String edad = request.getParameter("edad")+"";
        if(con.equals(con1)){
            String conH;
            try{
                MessageDigest md=MessageDigest.getInstance("SHA-256");
                md.update(con1.getBytes("UTF-8"));
                byte[] hash = md.digest();
                BigInteger bigInt = new BigInteger(1, hash);
                conH = bigInt.toString(16);
                while ( conH.length() < 32 ) {
                conH = "0"+conH;
                }
            }catch(NoSuchAlgorithmException | UnsupportedEncodingException e){conH="";}
            out.println("<html>");
            out.println("<head><title>Creacion de usuarios</title></head");
            out.println("<body>");
            out.println("<h1>DATOS INTRODUCIDOS</h1>");
            out.println("<table border)\"5\">");
            out.println("<tr><td><b>NOMBRE:</b></td><td><i>"+nom+"</i></td></tr>");
            out.println("<tr><td><b>EMAIL:</b></td><td><i>"+email+"</i></td></tr>");
            out.println("<tr><td><b>TELEFONO:</b></td><td><i>"+tel+"</i></td></tr>");
            out.println("<tr><td><b>EDAD:</b></td><td><i>"+edad+"</i></td></tr>");
            out.println("</table><p>");
            out.println("<a href=\"./CrearUsuario?conf=true&nombre="+nom+"&email="+email+"&tel="+tel+"&edad="+edad+"&hash="+conH+"\">aceptar</a>");
            out.println("<a href=\"CrearUsuario\">Volver</a>");
            out.println("</body>");
            out.println("</html>");
        }else{
            out.println("<html>");
            out.println("<head><title>Creacion de usuarios</title></head");
            out.println("<body>");
            out.println("<h1>CONTRASEÑAS NO COINCIDEN</h1>");
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String conf=request.getParameter("conf")+"";
        if(conf.equals("true")){
            String nom = request.getParameter("nombre");
            String email = request.getParameter("email");
            String tel = request.getParameter("tel");
            String edad = request.getParameter("edad");
            String con = request.getParameter("hash");
            if(bd.ejecutarUpdate("INSERT INTO usuarios (nombre,contrasena,email,telefono,edad) VALUES ('"+nom+"','"+con+"', '"+email+"' , "+tel+" , "+edad+" )").equals("1")){
                out.println("<html>");
                out.println("<head><title>Registro Creado</title></head");
                out.println("<body>");
                out.println("<p>Registro incluido correctamente en la base de datos</p>");
                out.println("<a href=\"CrearUsuario\">Crear otro usuario</a>");
                out.println("<a href=\"index.html\">Realizar otra accion</a>");
                out.println("</body>");
                out.println("</html>");
            }
            else{
                out.println("<html>");
                out.println("<head><title>ERROR</title></head");
                out.println("<body>");
                out.println("<p>ERROR AL CREAR EL REGISTRO, VUELVE A INTENTARLO</p>");
                out.println("<a href=\"CrearUsuario\">Crear otro usuario</a>");
                out.println("<a href=\"index.html\">Realizar otra accion</a>");
                out.println("</body>");
                out.println("</html>");        
            }
        }else{
            out.println("<html>");
            out.println("<head><title>Crear registro</title></head");
            out.println("<body>");
            out.println("<form method=\"post\" action=\"./CrearUsuario\">");
            out.println("<table>\n" +
                "          <tr>\n" +
                "              <td>Introduce tu nombre :</td>\n" +
                "              <td><input type=\"text\" name=\"nombre\" size=\"25\"></td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "              <td>Introduce tu email:</td>\n" +
                "              <td><input type=\"text\" name=\"email\" size=\"20\"></td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "              <td>Introduce tu contraseña:</td>\n" +
                "              <td><input type=\"text\" name=\"con\" size=\"20\"></td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "              <td>Repite tu contraseña:</td>\n" +
                "              <td><input type=\"text\" name=\"con1\" size=\"20\"></td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "              <td>Introduce tu teléfono:</td>\n" +
                "              <td><input type=\"text\" name=\"tel\" size=\"20\"></td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "              <td>Introduce tu edad:</td>\n" +
                "              <td><input type=\"text\" name=\"edad\" size=\"20\"></td>\n" +
                "          </tr>\n" +
                "      </table>");
            out.println("<input type=\"submit\" value=\"Enviar\">");
            out.println("<input type=\"reset\" value=\"Borrar\">");
            out.println("<a href=\"CrearUsuario\">Volver</a>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    public void init()  throws ServletException {
        bd = new ControladorBD();
    }
}