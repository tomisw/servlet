/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BorrarUsuario extends HttpServlet {
    
    protected ControladorBD bd;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<head><title>Borrar Registro</title></head");
            out.println("<body>");
            out.println("<form method=\"get\" action=\"./BorrarUsuario\">");
            out.println("<table>\n" +
                "          <tr>\n" +
                "              <td>Por nombre :</td>\n" +
                "              <td><input type=\"text\" name=\"nombre\" size=\"25\"></td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "              <td>Por email:</td>\n" +
                "              <td><input type=\"text\" name=\"email\" size=\"20\"></td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "              <td>Por teléfono:</td>\n" +
                "              <td><input type=\"text\" name=\"tel\" size=\"20\"></td>\n" +
                "          </tr>\n" +
                "          <tr>\n" +
                "              <td>Por edad:</td>\n" +
                "              <td><input type=\"text\" name=\"edad\" size=\"20\"></td>\n" +
                "          </tr>\n" +
                "      </table>");
            out.println("<input type=\"submit\" value=\"Enviar\">");
            out.println("<input type=\"reset\" value=\"Borrar\">");
            out.println("<a href=\"index.html\">Volver</a>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            if(request.getParameter("nombre")==null){
                processRequest(request, response);
            }else{
                String parametros[]=new String[4];
                parametros[0]=request.getParameter("nombre")+"";
                parametros[1]=request.getParameter("email")+"";
                parametros[2]=request.getParameter("tel")+"";
                parametros[3]=request.getParameter("edad")+"";
                ResultSet query=bd.ejecutarSentencia(BuscarUsuario.crearSentencia(parametros));
                String aux[][]=BuscarUsuario.extraerDatos(query);
                if(aux.length==0){
                    out.println("<html>");
                    out.println("<head><title>Busqueda de registros</title></head");
                    out.println("<body>");
                    out.println("<h1>NO HAY NINGÚN REGISTRO QUE SE PAREZCA AL QUE QUIERES BORRAR</h1>");
                    out.println("<a href=\"BorrarUsuario\">Volver</a>");
                    out.println("</body>");
                    out.println("</html>");
                }
                else{
                    out.println("<html>");
                    out.println("<head><title>Busqueda de registros</title></head");
                    out.println("<body>");
                    out.println("<h1>Los siguientes registros encajan en tus criterios de búsqueda. Introduce su contraseña para borrarlos</h1>");
                    out.println("<form method=\"post\" action=\"./BorrarUsuario\">");
                    for(int i=0;i<aux.length;i++){
                        out.println("<table>\n" +
                        "          <tr>\n" +
                        "              <td><b>Usuario: </b></td><td><i>"+aux[i][0]+"</i></td>\n" +
                        "              <td><b>Email: </b></td><td><i>"+aux[i][1]+"</i></td>\n" +
                        "              <td><b>Telefono: </b></td><td><i>"+aux[i][2]+"</i></td>\n" +
                        "              <td><b>Edad: </b></td><td><i>"+aux[i][3]+"</i></td>\n" +
                        "              <td><input type=\"hidden\" name=\"nombre"+i+"\" value=\""+aux[i][0]+"\"></td>\n" +
                        "          </tr>\n" +
                        "          <tr>\n" +
                        "              <td>Introduce la contraseña de este registro para borrarlo:</td>\n" +
                        "              <td><input type=\"text\" name=\"pass"+i+"\" size=\"20\"></td>\n" +
                        "          </tr>\n" +
                        "      </table>");
                        }
                        out.println("<input type=\"submit\" value=\"Enviar\">");
                        out.println("<input type=\"reset\" value=\"Borrar\">");
                        out.println("<a href=\"index.html\">Volver</a>");
                        out.println("</form>");
                    out.println("<table border)\"5\">");
                    out.println("</table><p>");            
                    out.println("<a href=\"BuscarUsuario\">Volver</a>");
                    out.println("</body>");
                    out.println("</html>");
            }
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        int i=0;
        String aux;
        while((aux=request.getParameter("nombre"+i+""))!=null){
            String conH;
            String cont = request.getParameter("pass"+i+"");
            try{
                MessageDigest md=MessageDigest.getInstance("SHA-256");
                md.update(cont.getBytes("UTF-8"));
                byte[] hash = md.digest();
                BigInteger bigInt = new BigInteger(1, hash);
                conH = bigInt.toString(16);
                while ( conH.length() < 32 ) {
                conH = "0"+conH;
                }
            }catch(NoSuchAlgorithmException | UnsupportedEncodingException e){conH="";}
            bd.ejecutarUpdate("delete from usuarios where upper(nombre)=\""+aux+"\"and contrasena=\""+conH+"\"");
            i++;
        }
        out.println("<html>");
        out.println("<head><title>Busqueda de registros</title></head");
        out.println("<body>");
        out.println("<h1>NO HAY NINGÚN REGISTRO QUE SE PAREZCA AL QUE QUIERES BORRAR</h1>");
        out.println("<a href=\"BorrarUsuario\">Volver</a>");
        out.println("</body>");
        out.println("</html>");
    }
    public void init()  throws ServletException {
        bd = new ControladorBD();
    }
}
