package modulo.moduloCNT;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InfinitContentServlet
 */
public class InfinitContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Integer counter = 1;

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String resp = "";
            for (int i = 1; i <= 10; i++) {
                resp += "<p><span>"
                        + counter++
                        + "</span> This is the dynamic content served freshly from server</p>";
            }
            out.write(resp);
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

}
