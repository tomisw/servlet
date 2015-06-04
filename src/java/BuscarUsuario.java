import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

public class BuscarUsuario extends HttpServlet {
    protected ControladorBD bd;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<head><title>Buscar Registro</title></head");
            out.println("<body>");
            out.println("<form method=\"post\" action=\"./BuscarUsuario\">");
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
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            String parametros[]=new String[4];
            parametros[0]=request.getParameter("nombre")+"";
            parametros[1]=request.getParameter("email")+"";
            parametros[2]=request.getParameter("tel")+"";
            parametros[3]=request.getParameter("edad")+"";
            ResultSet query=bd.ejecutarSentencia(crearSentencia(parametros));
            String aux[][]=extraerDatos(query);
            if(aux.length==0){
                out.println("<html>");
                out.println("<head><title>Busqueda de registros</title></head");
                out.println("<body>");
                out.println("<h1>NO HAY DATOS QUE COINCIDAN CON TUS CRITERIOS DE BÚSQUEDA</h1>");
                out.println("<a href=\"BuscarUsuario\">Volver</a>");
                out.println("</body>");
                out.println("</html>");
            }
            else{
                out.println("<html>");
                out.println("<head><title>Busqueda de registros</title></head");
                out.println("<body>");
                out.println("<h1>DATOS ENCONTRADOS</h1>");
                for(int i=0;i<aux.length;i++){
                    out.println("<table border)\"5\">");
                    out.println("<h2><tr><td><b>Usuario: </b></td><td><i>"+aux[i][0]+"</i></td></tr></h2>");
                    out.println("<tr><td><b>Email: </b></td><td><i>"+aux[i][1]+"</i></td></tr>");
                    out.println("<tr><td><b>Tel: </b></td><td><i>"+aux[i][2]+"</i></td></tr>");
                    out.println("<tr><td><b>Edad: </b></td><td><i>"+aux[i][3]+"</i></td></tr>");
                    out.println("</table>"); 
                }
                out.println("<table border)\"5\">");
                out.println("</table><p>");            
                out.println("<a href=\"BuscarUsuario\">Volver</a>");
                out.println("</body>");
                out.println("</html>");
            }
    }
    public void init()  throws ServletException {
        bd = new ControladorBD();
    }
    public static String crearSentencia(String[] s){
        String select="SELECT * FROM USUARIOS";
        int cont=0;
        
        if(!s[0].isEmpty()){
            select=select.concat(" WHERE upper(nombre)='"+s[0]+"'");
            cont=1;
        }
        
        if(!s[1].isEmpty()){
            if(cont==0){
                select+=" WHERE upper(email)='"+s[1]+"'";
                cont=1;
            }
            else
                select+=" and upper(email)='"+s[1]+"'";
        }
        
        if(!s[2].isEmpty()){
            if(cont==0){
                select+=" WHERE upper(telefono)='"+s[2]+"'";
                cont=1;
            }
            else
                select+=" and upper(telefono)='"+s[2]+"'";
        }
        
        if(!s[3].isEmpty()){
            if(cont==0){
                select+=" WHERE edad="+s[3]+"";
            }
            else
                select+=" and edad="+s[3]+"";
        }
        return select;
    }
    public static String[][] extraerDatos(ResultSet query){
        String aux[][];
        if(query!=null){
            try{
                query.last();
                aux=new String[query.getRow()][4];
                if(query.first()){
                    int cont=0;
                    do{
                        aux[cont][0]=query.getString(2);
                        aux[cont][1]=query.getString(4);
                        aux[cont][2]=query.getString(5);
                        aux[cont][3]=query.getString(6);
                        cont++;
                    }while(query.next());
                }
            }catch(SQLException e){aux=new String[0][0];}
        }
        else{
            aux=new String[0][0];
        }
        return aux;
    }
}
