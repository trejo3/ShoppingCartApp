package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the LINEITEMS database table.
 * 
 */
@Entity
@Table(name="LINEITEMS", schema="testuserdb")
@NamedQuery(name="Lineitem.findAll", query="SELECT l FROM Lineitem l")
public class Lineitem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(schema="testuserdb", name="LINEITEMS_LINEITEMID_GENERATOR", sequenceName="LINEITEMS_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LINEITEMS_LINEITEMID_GENERATOR")
	@Column(name="LINEITEM_ID")
	private long lineitemId;

	@Column(name="P_NAME")
	private String pName;

	@Column(name="P_PRICE")
	private double pPrice;

	@Column(name="PRODUCT_ID")
	private long productId;

	private int quantity;

	public Lineitem() {
	}

	public long getLineitemId() {
		return this.lineitemId;
	}

	public void setLineitemId(long lineitemId) {
		this.lineitemId = lineitemId;
	}

	public String getPName() {
		return this.pName;
	}

	public void setPName(String pName) {
		this.pName = pName;
	}

	public double getPPrice() {
		return this.pPrice;
	}

	public void setPPrice(double pPrice) {
		this.pPrice = pPrice;
	}

	public long getProductId() {
		return this.productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}