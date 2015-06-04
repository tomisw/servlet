
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Index extends HttpServlet {

    protected String usuario;
    protected ControladorBD bd;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>\n" +
                    "    <head>\n" +
                    "        <title>Pagina web de registros</title>\n" +
                    "        <meta charset=\"UTF-8\">\n" +
                    "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    </head>\n" +
                    "    <body>\n" +
                    "        <h2>Elije que deseas hacer</h2>\n" +
                    "        <a href=\"CrearUsuario\">Crear un nuevo registro</a>\n" +
                    "        <a href=\"ModificarUsuario\">Modificar el registro actual</a>\n" +
                    "        <a href=\"BorrarUsuario\">Eliminar el registro actual</a>\n" +
                    "        <a href=\"BuscarUsuario\">Buscar un registro</a>\n" +
                    "    </body>\n" +
                    "</html>");
        }
    }

    @Override
    public void init() throws ServletException {
        bd = new ControladorBD();
    }
}
