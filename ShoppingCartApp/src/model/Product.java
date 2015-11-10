package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the PRODUCTS database table.
 * 
 */
@Entity
@Table(name="PRODUCTS", schema="testuserdb")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PRODUCT_ID")
	private long productId;

	@Column(name="P_DESCRIPTION")
	private String pDescription;

	@Column(name="P_NAME")
	private String pName;

	private double price;

	public Product() {
	}

	public long getProductId() {
		return this.productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getPDescription() {
		return this.pDescription;
	}

	public void setPDescription(String pDescription) {
		this.pDescription = pDescription;
	}

	public String getPName() {
		return this.pName;
	}

	public void setPName(String pName) {
		this.pName = pName;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}