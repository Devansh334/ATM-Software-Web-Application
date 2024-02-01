/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DEVANSH GOYAL
 */
public class MiniState extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //out.print("<form action='Clear' ><input type='submit' value='clear' style='position: fixed; top: 10px; right: 15%;color: whitesmoke;background: graytext;border-radius: 10px'></form>");

        String cardid = (String)request.getSession().getAttribute("cardid");
       
        RequestDispatcher rd =request.getRequestDispatcher("css.html");
        rd.include(request, response);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
    
    Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bank","root","root");
    
    Statement smt = cn.createStatement();
    
    ResultSet rs = smt.executeQuery("select * from "+cardid+"");
    out.print("<table id='tab'  border='2' cellspacing='5' cellpadding='5'>");
    out.print("<tr><th>Time Stamp</th><th>Transaction</th></tr>");
    while(rs.next()){
        String str =rs.getString("transactionm");
        if(str.contains("-")){
        out.print("<tr><td><b>"+rs.getString("time")+"</b></td><td style='color:red'><b>"+rs.getString("transactionm")+"</b></td></tr>");
        }
        if(str.contains("+")){
        out.print("<tr><td><b>"+rs.getString("time")+"</b></td><td style='color:green'><b>"+rs.getString("transactionm")+"</b></td></tr>");
       
        }
        
    }
    out.print("</table>");
    out.print("<br>");
    out.print("<form action='cont.html'><input id='b' type='submit' value='Okay'></form>");
           
    cn.close();
        }
        catch(ClassNotFoundException | SQLException  e){
        RequestDispatcher r= request.getRequestDispatcher("Fail");
             r.forward(request, response);
        
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
