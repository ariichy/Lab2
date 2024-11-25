package Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import DAO.IndyWinnerDAO;
import DAO.WinnerDAO;
import Model.IndyWinner;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * This class is a servlet that handles HTTP GET requests for displaying
 * a paginated list of IndyWinners. It uses a DAO to fetch data from the
 * database and forwards that data to the displayWinners.jsp to display
 * it.
 */
@WebServlet(urlPatterns = {"/IndyWinners"})
public class WinnerServlet extends HttpServlet {
    private static final int PAGE_SIZE = 10;

    public WinnerDAO dao; // added strictly for testing

    /**
     * Handles the HTTP GET request by retrieving a paginated list of winners,
     * calculating pagination details, and forwarding the data to a JSP page.
     * @param request the object that contains the request from the client
     * @param response the object that contains the response the servlet made
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int page = Integer.parseInt(request.getParameter("page") == null ? "1" : request.getParameter("page"));
            int offset = (page - 1) * PAGE_SIZE;

            WinnerDAO dao = new IndyWinnerDAO();
            List<IndyWinner> winners = dao.getWinners(offset, PAGE_SIZE);
            int totalCount = dao.getTotalCount();
            int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE);

            // Set the data in the request
            request.setAttribute("winners", winners);
            request.setAttribute("page", page);
            request.setAttribute("totalPages", totalPages);

            // Forward to the JSP for rendering
            RequestDispatcher dispatcher = request.getRequestDispatcher("/displayWinners.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException | NumberFormatException ex) {
            // Handle error
            request.setAttribute("error", ex.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            dispatcher.forward(request, response);
        }
    }
}

