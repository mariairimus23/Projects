package businessLayer;

import java.util.List;

import java.util.NoSuchElementException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import dataAccessLayer.ProductDAO;
import model.Product;

/**
 * Date Apr 15-2020 
 * Aceasta clasa descrie obiectul ProductBll si apeleaza
 * metodele din clasa ProductDAO, implementeaza logica aplicatiei
 * 
 * @author Irimus Maria
 */
public class ProductBLL {

	private ProductDAO productDAO = new ProductDAO();

	public ProductBLL() {
	}

	/**
	 * Gaseste produsul dupa nume
	 * 
	 * @param name numele produsului cautat
	 * @return produsul gasit
	 */
	public Product findProductByName(String name) {
		Product product = ProductDAO.findByName(name);
		if (product == null) {
			throw new NoSuchElementException("The product with name =" + name + " was not found!");
		}
		return product;
	}

	/**
	 * Insereaza un produs
	 * 
	 * @param product ce trebuie inserat
	 * @return id-ul produsului inserat
	 */
	public int insertProduct(Product product) {

		return ProductDAO.insert(product);
	}

	/**
	 * Actualizeaza datele despre un produs
	 * 
	 * @param product ce trebuie actualizat
	 */
	public void updateProduct(Product product) {

		ProductDAO.update(product);
	}

	/**
	 * Sterge din tabela un produs
	 * 
	 * @param product     ce trebuie sters
	 * @param productName numele dupa care este cautat produsul
	 * @return id-ul produsului sters
	 */
	public String deleteProduct(Product product, String productName) {

		return ProductDAO.delete(product, productName);
	}

	/**
	 * Gaseste toate produsele din tabela
	 * 
	 * @return lista tuturor produselor din tabela
	 */
	public List<Product> findAll() {

		return productDAO.findAll();
	}

	/**
	 * Verifica ce cantitate de produs exista in tabela si actualizeaza daca este
	 * cazul
	 * 
	 * @param name     numele produsului comandat
	 * @param quantity cantitatea de produs din tabela
	 * @return 0 daca actualizarea produsului a fost efectuata cu succes, -1 daca
	 *         stocul este insuficient si -2 daca nu exista clientul in baza de date
	 */
	public double verificare(String name, int quantity) {

		double ok = 0;
		Product p = ProductDAO.findByName(name);

		if (p != null) {
			if (p.getQuantity() >= quantity) {

				p.setQuantity(p.getQuantity() - quantity);
				ProductDAO.update(p);
			} else {
				ok = -1;
			}
		} else {
			ok = -2;
		}
		return ok;
	}

	/**
	 * Actualizeaza cantitatea daca este introdus acelasi produs de mai multe ori
	 * 
	 * @param name     numele produsului
	 * @param quantity de produs din tabela
	 * @return 0 daca daca produsul exista deja in tabela, -1 in caz contrar
	 */
	public int updateQuantity(String name, int quantity) {

		int found = 0;
		Product product = ProductDAO.findByName(name);

		if (product != null) {
			product.setQuantity(product.getQuantity() + quantity);
			ProductDAO.update(product);
		} else {
			found = -1;
		}
		return found;
	}

	/**
	 * Scrierea datelor din mySQL in documentul de tip pdf
	 * 
	 * @param products lista de produse din tabela
	 * @param document documentul in care se vor scrie datele
	 * @throws DocumentException exceptie de tip document
	 */
	public void writeInPdf(List<Product> products, Document document) throws DocumentException {

		Paragraph paragraph = new Paragraph("Product report\n\n");
		document.add(paragraph);

		PdfPTable table = new PdfPTable(3);
		PdfPCell header1 = new PdfPCell(new Phrase("Name"));
		header1.setBackgroundColor(BaseColor.ORANGE);
		header1.setBorderWidth(2);
		table.addCell(header1);
		PdfPCell header2 = new PdfPCell(new Phrase("Quantity"));
		header2.setBackgroundColor(BaseColor.ORANGE);
		header2.setBorderWidth(2);
		table.addCell(header2);
		PdfPCell header3 = new PdfPCell(new Phrase("Price"));
		header3.setBackgroundColor(BaseColor.ORANGE);
		header3.setBorderWidth(2);
		table.addCell(header3);

		for (int i = 0; i < products.size(); i++) {

			table.addCell(products.get(i).getName());
			table.addCell(Integer.toString(products.get(i).getQuantity()));
			table.addCell(Double.toString(products.get(i).getPrice()));
		}

		document.add(table);
		document.close();
		System.out.println("Product report is finished");
	}
}