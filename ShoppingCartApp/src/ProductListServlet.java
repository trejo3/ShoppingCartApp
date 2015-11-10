

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
 * Servlet implementation class ProductListServlet
 */
@WebServlet("/ProductListServlet")
public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		getProducts(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		getProducts(request, response);
	}
	
	public void getProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = customTools.DBUtil.getEmFactory().createEntityManager();
		String q="SELECT p FROM Product p";
		TypedQuery<Product> qT = em.createQuery(q, Product.class);
		List<Product> productsL = null;
		String tableinfo  = "";
		
		Product myProduct = new Product();
		
		try
		{
			productsL=qT.getResultList();
			for(int i=0;i<productsL.size();i++)
			{
				
				//get ID as well and pass it on
				long pID = productsL.get(i).getProductId();
				tableinfo += "<tr><td>" + productsL.get(i).getPName()+"</td><td>" + productsL.get(i).getPDescription()+"</td><td>" + productsL.get(i).getPrice()+"</td><td><a href=\"InsertQuantityServlet?id="+pID+"\">" + "Add to Cart"+"</td></tr>";
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
		.getRequestDispatcher("/ProductList.jsp")
		.forward(request, response);

	}

}
