import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Date;
import java.net.InetAddress;
import java.net.UnknownHostException;

@WebServlet("/session")
public class SessionServlet extends HttpServlet {



  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String title = "Simple HttpSession Demo";
    String heading;
    String serverIPAddress = "Unknown";
    Integer accessCount = new Integer(0);;
    if (session.isNew()) {
      heading = "Hello stranger";
    } else {
      heading = "Welcome back friend";
      Integer oldAccessCount =
        (Integer)session.getAttribute("accessCount");
      if (oldAccessCount != null) {
        accessCount =
          new Integer(oldAccessCount.intValue() + 1);
      }
    }
    session.setAttribute("accessCount", accessCount);


    try {
       InetAddress localhost = InetAddress.getLocalHost();
       serverIPAddress = localhost.getHostAddress().trim();
    }
    catch (UnknownHostException e) {
        System.err.println("Error: cannot get server IP Address  : " + e.getMessage());
    }

    out.println("<HTML><HEAD><TITLE>"+title+"</TITLE>\n" +
                "<meta http-equiv=\"Cache-Control\" content=\"no-cache, no-store, must-revalidate\">\n" +
                "<meta http-equiv=\"Pragma\" content=\"no-cache\">\n" +
                "<meta http-equiv=\"Expires\" content=\"0\">\n" +
                "</HEAD>\n" +
                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=\"CENTER\">" + heading + "</H1>\n" +
                "<H2>Information on your HTTPSession:</H2>\n" +
                "<TABLE BORDER=1 ALIGN=CENTER>\n" +
                "<TR BGCOLOR=\"#FFAD00\">\n" +
                "<TH>Field name</TH><TH>Value</TH>\n" +
                "</TR>\n" +
                "<TR>\n" +
                "  <TD>ID</TD>\n" +
                "  <TD>" + session.getId() + "</TD>\n" +
                "</TR>\n" +
                "<TR>\n" +
                "  <TD>Creation time</TD>\n" +
                "  <TD>" + new Date(session.getCreationTime()) + "</TD>\n" +
                "</TR>\n" +
                "<TR>\n" +
                "  <TD>Time of last access</TD>\n" +
                "  <TD>" + new Date(session.getLastAccessedTime()) + "</TD>\n" +
                "</TR>\n" +
                "<TR>\n" +
                "  <TD>Number of previous accesses</TD>\n" +
                "  <TD>" + accessCount + "</TD>\n" +
                "</TR>\n" +
                "<TR>\n" +
                "  <TD>POD IP address</TD>\n" +
                "  <TD>" + serverIPAddress + "</TD>\n" +
                "</TR>\n" +
                "</TABLE>\n" +
                "</BODY></HTML>");

  }

  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }
}
