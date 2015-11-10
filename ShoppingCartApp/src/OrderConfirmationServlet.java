

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import customTools.LineItemsDB;
import model.Lineitem;
import model.Product;

/**
 * Servlet implementation class ShoppingCartServlet
 */
@WebServlet("/OrderConfirmationServlet")
public class OrderConfirmationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderConfirmationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
    	populateOrder(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		populateOrder(request, response);
	}	

	
	
	public void populateOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		EntityManager em = customTools.DBUtil.getEmFactory().createEntityManager();
		String q="SELECT l FROM Lineitem l";
		TypedQuery<Lineitem> qT = em.createQuery(q, Lineitem.class);
		List<Lineitem> lineitemsL = null;
		String tableinfo  = "";
		double subtotal=0;
		double tax=0;
		double total=0;
		
		try
		{
			lineitemsL=qT.getResultList();
			double totalPrice = 0;
			
			for(int i=0;i<lineitemsL.size();i++)
			{
				totalPrice=(lineitemsL.get(i).getPPrice()*lineitemsL.get(i).getQuantity());
				
				subtotal += totalPrice;
				
				tableinfo += "<tr><td>" + lineitemsL.get(i).getProductId()+"</td><td>" + lineitemsL.get(i).getPName()+"</td><td>" + lineitemsL.get(i).getPPrice()+"</td><td>" + lineitemsL.get(i).getQuantity()+"</td><td>" +totalPrice+"</td></tr>";
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally{
			em.close();
			System.out.println("cerrado!");
		}
		tax = subtotal*.10;
		total = subtotal+tax;

		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String fSubtotal = formatter.format(subtotal);
		String fTax = formatter.format(tax);
		String fTotal = formatter.format(total);
		
		request.setAttribute("tableinfo", tableinfo);
		request.setAttribute("subtotal", fSubtotal);
		request.setAttribute("tax", fTax);
		request.setAttribute("total", fTotal);
		
		getServletContext()
		.getRequestDispatcher("/OrderConfirmation.jsp")
		.forward(request, response); 
	}
	
}