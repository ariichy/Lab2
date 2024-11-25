package Tests;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import Model.IndyWinner;
import DAO.WinnerDAO;
import Servlet.WinnerServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

class WinnerServletTest {

    private WinnerServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private WinnerDAO dao;

    @BeforeEach
    void setUp() {
        servlet = new WinnerServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        dao = mock(WinnerDAO.class);

        // Replace the DAO in the servlet (dependency injection pattern)
        servlet.dao = dao; // Modified Servlet.WinnerServlet to accept DAO for testing
    }

    @Test
    void testDoGetHappyPath() throws ServletException, IOException, SQLException {
        // Arrange
        when(request.getParameter("page")).thenReturn("1");
        when(request.getRequestDispatcher("/displayWinners.jsp")).thenReturn(dispatcher);

        List<IndyWinner> winners = Arrays.asList(new IndyWinner(2020, "Driver1", 160.0, "CAN"),
                new IndyWinner(202, "Driver2", 170.0, "USA"));
        when(dao.getWinners(0, 10)).thenReturn(winners);
        when(dao.getTotalCount()).thenReturn(20);

        // Act
        servlet.doGet(request, response);

        // Assert
        verify(request).setAttribute("winners", winners);
        verify(request).setAttribute("page", 1);
        verify(request).setAttribute("totalPages", 2);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoGetDefaultPage() throws ServletException, IOException, SQLException {
        // Arrange
        when(request.getParameter("page")).thenReturn(null); // No page parameter
        when(request.getRequestDispatcher("/displayWinners.jsp")).thenReturn(dispatcher);

        List<IndyWinner> winners = Arrays.asList(new IndyWinner(2020, "Driver1", 160.0, "CAN"));
        when(dao.getWinners(0, 10)).thenReturn(winners);
        when(dao.getTotalCount()).thenReturn(15);

        // Act
        servlet.doGet(request, response);

        // Assert
        verify(request).setAttribute("winners", winners);
    }
}