

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

import model.Product;

/**
 * Servlet implementation class InsertQuantityServlet
 */
@WebServlet("/InsertQuantityServlet")
public class InsertQuantityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertQuantityServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		getDesiredProduct(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		getDesiredProduct(request, response);
	}
	
	public void getDesiredProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = customTools.DBUtil.getEmFactory().createEntityManager();
//		HttpSession session = request.getSession();
		
		String q="SELECT p FROM Product p where p.productId = :id";
		TypedQuery<Product> qT = em.createQuery(q, Product.class);
		qT.setParameter("id", Long.parseLong(request.getParameter("id")));
		
		Product desiredProduct = null;
		
		try
		{
			desiredProduct = qT.getSingleResult();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally{
			em.close();
			System.out.println("cerrado!");
		}

		request.setAttribute("prodID", request.getParameter("id"));
		request.setAttribute("prodName", desiredProduct.getPName());
		request.setAttribute("prodPrice", desiredProduct.getPrice());
		
		
		/*		session.setAttribute("prodID", desiredProduct.getProductId());
		session.setAttribute("prodName", desiredProduct.getPName());
		session.setAttribute("prodPrice", desiredProduct.getPrice());
*/		
		getServletContext()
		.getRequestDispatcher("/AddQuantity.jsp")
		.forward(request, response);
	}

}
