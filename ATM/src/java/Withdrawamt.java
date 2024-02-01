/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DEVANSH GOYAL
 */
public class Withdrawamt extends HttpServlet {

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
        
        
      
        PrintWriter pw= response.getWriter();
        
       
        String cardid = (String)request.getSession().getAttribute("cardid");
        String pin = request.getParameter("wpin");
          
     String wamt = request.getParameter("wamt");
      //pw.print(cardid+" : "+" : "+pin+" : "+wamt);
        
       request.getSession(true).setAttribute("wamt", wamt);
        
       
       try {
            
           Class.forName("com.mysql.cj.jdbc.Driver");
    
    Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bank","root","root");
    
    Statement smt = cn.createStatement();
    
    ResultSet rs = smt.executeQuery("select * from ATM where cardid ='"+cardid+"' and pin = '"+pin+"'");
    
    if(rs.next()){
         
         int amt = Integer.parseInt(wamt);
        int bal =rs.getInt("balance")- amt;
        
        //pw.print(bal);
       
        int i=smt.executeUpdate("UPDATE ATM SET balance= "+bal+" where cardid='"+cardid+"'");
        Date d =new Date();
        String t = "-Debit  Rs. "+wamt+" self";
        int j=smt.executeUpdate("insert into "+cardid+" values('"+d+"','"+t+"')");
        
        
        if(i>0 &&  j>0){
            
            RequestDispatcher r= request.getRequestDispatcher("Show");
           r.forward(request, response);
        }
        
        
    }
       else{
            
             RequestDispatcher r= request.getRequestDispatcher("Fail");
             r.forward(request, response);
        
    }
    cn.close();
          
        }
        catch(IOException | ClassNotFoundException | NumberFormatException | SQLException | ServletException e){
         
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
