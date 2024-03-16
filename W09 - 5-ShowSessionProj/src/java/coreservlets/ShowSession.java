package coreservlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

/**
 * Servlet that uses session-tracking to keep per-client access counts. Also
 * shows other info about the session.
 * <P>
 * Taken from Core Servlets and JavaServer Pages 2nd Edition from Prentice Hall
 * and Sun Microsystems Press, http://www.coreservlets.com/. &copy; 2003 Marty
 * Hall; may be freely used or adapted.
 */
public class ShowSession extends HttpServlet {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        String heading;
        Integer accessCount = (Integer) session.getAttribute("accessCount");
        if (accessCount == null) {
            accessCount = new Integer(0);
            heading = "Welcome, Newcomer";
        } else {
            heading = "Welcome Back";
            accessCount = new Integer(accessCount.intValue() + 1);
        }
        // Integer is an immutable data structure. So, you
        // cannot modify the old one in-place. Instead, you
        // have to allocate a new one and redo setAttribute.
        session.setAttribute("accessCount", accessCount);
        session.setAttribute("getHeading", "string of the heading");

        Cookie[] cookies = request.getCookies();

//        if (cookies.length == 0) {
            Cookie cookie = new Cookie("auth", "myAuth");
            response.addCookie(cookie);
            
            Cookie cookie2 = new Cookie("our_cookie", "chocolate");
            response.addCookie(cookie2);
//        }

//        response.addCookie(cookie);
        PrintWriter out = response.getWriter();
        String title = "Session Tracking Example";
        String docType
                = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 "
                + "Transitional//EN\">\n";
        out.println(docType
                + "<HTML>\n"
                + "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n"
                + "<BODY BGCOLOR=\"#FDF5E6\">\n"
                + "<CENTER>\n"
                + "<H1>" + heading + "</H1>\n"
                + "<H2>Information on Your Session:</H2>\n"
                + "<TABLE BORDER=1>\n"
                + "<TR BGCOLOR=\"#FFAD00\">\n"
                + "  <TH>Info Type<TH>Value\n"
                + "<TR>\n"
                + "  <TD>ID\n"
                + "  <TD>" + session.getId() + "\n"
                + "<TR>\n"
                + "  <TD>Creation Time\n"
                + "  <TD>"
                + new Date(session.getCreationTime()) + "\n"
                + "<TR>\n"
                + "  <TD>Time of Last Access\n"
                + "  <TD>"
                + new Date(session.getLastAccessedTime()) + "\n"
                + "<TR>\n"
                + "<TR>\n"
                + "  <TD>Heading\n"
                + "  <TD>"
                + session.getAttribute("getHeading") + "\n"
                + "<TR>\n"
                + "  <TD>Number of Previous Accesses\n"
                + "  <TD>" + accessCount + "\n"
                + "</TABLE>\n"
                + "</CENTER>");
        out.println("");

        out.println("<table border=\"1\">");
        out.println("<tr>");
        out.println("<td>Name</td>");
        out.println("<td>Value</td>");
        out.println("</tr>");
        for (Cookie cooky : cookies) {
            out.printf("<tr><td>%s</td><td>%s</td></tr>", cooky.getName(), cooky.getValue());
        }
        out.println("</table>");
        out.println(session.getMaxInactiveInterval());
        
        out.println("</BODY></HTML>");
    }
}
