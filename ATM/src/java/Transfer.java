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
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DEVANSH GOYAL
 */
public class Transfer extends HttpServlet {

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
        String pin = request.getParameter("pin");
        String transid = (String) request.getSession(false).getAttribute("transid"); 
        String transamt = (String) request.getSession(false).getAttribute("transamt");
        int amt = Integer.parseInt(transamt);
        try{
             Class.forName("com.mysql.cj.jdbc.Driver");
    
    Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bank","root","Dev_123goyal");
    
    Statement smt = cn.createStatement();
    
    ResultSet rs = smt.executeQuery("select * from ATM where cardid ='"+cardid+"' and pin = '"+pin+"'");
    
    if(rs.next()){
       int bal1 = rs.getInt("balance");
       String name1= rs.getString("name");
       ResultSet r = smt.executeQuery("select * from ATM where cardid ='"+transid+"' ");
        
       if(r.next()){
           String name2= r.getString("name");
                     if (bal1-100>=amt) {
                         int bal2=r.getInt("balance");
                         int b1=bal1-amt;
                         int b2=bal2+amt;
                         
                         int i = smt.executeUpdate("update ATM set balance="+b1+" where cardid='"+cardid+"' ");
                         int j = smt.executeUpdate("update ATM set balance="+b2+" where cardid='"+transid+"' ");
                         
                         
                         Date d =new Date();
                        
                         
                        String t1 = "-Debit by Transfer Rs.  "+transamt+" to "+name2+" "+transid;
                        String t2 = "+Credit by Transfer Rs. "+transamt+" by "+name1+" "+cardid;
                        
                        int x=smt.executeUpdate("insert into "+cardid+" values('"+d+"','"+t1+"')");
                        int y=smt.executeUpdate("insert into "+transid+" values('"+d+"','"+t2+"')");
        
       
                         
                         if(i>0 && j>0 && x>0 && y>0){
                             RequestDispatcher rm= request.getRequestDispatcher("cont.html");
                                rm.forward(request, response);
                         
                         }
                         else{
                             RequestDispatcher rm= request.getRequestDispatcher("Fail");
                                rm.forward(request, response);
                         }
                         
                     } 
                     else{ out.print("TRANSACTION FAILED ! NOT ENOUGH BALANCE");
                            RequestDispatcher rd = request.getRequestDispatcher("login.html");
                            rd.include(request, response);
                            
                     }
           
           
           
           
       }
        
    }
     else{
         RequestDispatcher r= request.getRequestDispatcher("Fail");
         r.forward(request, response);
    }
    cn.close();
 
        }
        catch(IOException | ClassNotFoundException | SQLException | ServletException e){
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
