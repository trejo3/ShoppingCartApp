

import java.io.IOException;
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
@WebServlet("/ShoppingCartServlet")
public class ShoppingCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		generateSC(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		generateSC(request, response);
	}
	
	public void generateSC(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		EntityManager em = customTools.DBUtil.getEmFactory().createEntityManager();
		
		if  (action.equals("pull")){
			pullProducts(request,response);
		}
		
		
		if  (action.equals("addToCart")){
			addProduct(request, response);
			pullProducts(request,response);
		}
			
	}		
	
	public void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		HttpSession session = request.getSession();
		Lineitem newLineitem = new Lineitem();

		newLineitem.setProductId(Long.parseLong(request.getParameter("prodID")));
		newLineitem.setPName(request.getParameter("prodName"));
		newLineitem.setPPrice(Double.parseDouble(request.getParameter("prodPrice")));
		newLineitem.setQuantity(Integer.parseInt(request.getParameter("qty")));
//		newLineitem.setLineitemId(2L);
		
		
		/*		long prodID = (long) session.getValue("prodID");
		newLineitem.setProductId(prodID);
		newLineitem.setPName((String) session.getValue("prodName"));
		newLineitem.setPPrice((double) session.getValue("prodPrice"));
		newLineitem.setQuantity(Integer.parseInt(request.getParameter("qty")));
*/		
		
		
		LineItemsDB.insert(newLineitem);
	}
	
	
	public void pullProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		EntityManager em = customTools.DBUtil.getEmFactory().createEntityManager();
		String q="SELECT l FROM Lineitem l";
		TypedQuery<Lineitem> qT = em.createQuery(q, Lineitem.class);
		List<Lineitem> lineitemsL = null;
		String tableinfo  = "";
		
		try
		{
			lineitemsL=qT.getResultList();
			double totalPrice = 0;
			for(int i=0;i<lineitemsL.size();i++)
			{
				totalPrice=(lineitemsL.get(i).getPPrice()*lineitemsL.get(i).getQuantity());
				
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
		request.setAttribute("tableinfo", tableinfo);
		
		getServletContext()
		.getRequestDispatcher("/ShoppingCart.jsp")
		.forward(request, response); 
	}
	
}
