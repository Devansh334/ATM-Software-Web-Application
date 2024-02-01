/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DEVANSH GOYAL
 */
public class Idcheck extends HttpServlet {

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
        String cardid = (String)request.getSession().getAttribute("cardid");
       
        String transid = request.getParameter("transid");
        //out.print(transid);
        
        try{
             Class.forName("com.mysql.cj.jdbc.Driver");
    
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bank","root","root");
    
            Statement smt = cn.createStatement();
    
            ResultSet rst = smt.executeQuery("select * from ATM where binary cardid = '"+transid+"'");
    
    if(rst.next() ){
        
        
        if(!transid.equals(cardid)){
        request.getSession(true).setAttribute("transid", transid);
        RequestDispatcher rd = request.getRequestDispatcher("transamt.html");
        rd.forward(request, response);
        }
        else{
        out.print("No Account Found for given Card ID");
        RequestDispatcher rd = request.getRequestDispatcher("transferid.html");
        rd.include(request, response);
        
    }
    
    }
    else{
        out.print("No Account Found for given Card ID");
        RequestDispatcher rd = request.getRequestDispatcher("transferid.html");
        rd.include(request, response);
        
    }
    cn.close();
        }
        catch (ClassNotFoundException | SQLException  e){
            out.print("TRANSACTION FAILED ! SOMETHING WENT WRONG");
            RequestDispatcher rd = request.getRequestDispatcher("login.html");
            rd.include(request, response);
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
