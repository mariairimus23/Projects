package presentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Scanner;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

import businessLayer.BillBLL;
import businessLayer.ClientBLL;
import businessLayer.ProductBLL;
import model.Bill;
import model.Client;
import model.Product;

/**
 * Date Apr 15-2020 
 * Aceasta clasa realizeaza convertirea datelor din fisierul de
 * intrare, implementeaza intrarea / iesirea utilizatorului
 * 
 * @author Irimus Maria
 */
public class Parser {

	private Scanner scanner;
	private String input;

	ClientBLL clientBll = new ClientBLL();
	ProductBLL productBll = new ProductBLL();
	BillBLL billBll = new BillBLL();

	public Parser() {

	}

	/**
	 * Deschide fisierul text de intrare
	 */
	public void open() {
		try {
			scanner = new Scanner(new File(this.input));
		} catch (Exception e) {
			System.out.println("could not find file");
		}
	}

	/**
	 * Extragerea din fisierul text a instructiunii de inserare si informatii despre
	 * obiectul ce trebuie adaugat in baza de date
	 * 
	 * @param val String ce contine pe rand cate o linie din fisierul text
	 */
	public void insert(String[] val) {

		if (val[0].equals("Insert")) {
			if (val[1].equals("client:")) {
				String[] n = val[3].split(",");
				Client client = new Client();
				client.setName(val[2] + " " + n[0]);
				client.setAddress(val[4]);
				client.setId(clientBll.insertClient(client));

			} else {
				if (val[1].equals("product:")) {
					Product product = new Product();
					String[] n = val[2].split(",");
					product.setName(n[0]);
					n = val[3].split(",");
					product.setQuantity(Integer.parseInt(n[0]));
					product.setPrice(Double.parseDouble(val[4]));
					if (productBll.updateQuantity(product.getName(), product.getQuantity()) == -1) {
						product.setId(productBll.insertProduct(product));
					}
				}
			}
		}
	}

	/**
	 * Extragerea din fisierul text a instructiunii de stergere si informatii despre
	 * obiectul ce trebuie sters din baza de date
	 * 
	 * @param val String ce contine pe rand cate o linie din fisierul text
	 */
	public void delete(String[] val) {

		if (val[0].equals("Delete")) {
			if (val[1].equals("client:")) {
				String[] n = val[3].split(",");
				Client client = new Client();
				client.setName(val[2] + " " + n[0]);
				Client c = clientBll.findClientByName(client.getName());
				clientBll.deleteClient(c, c.getName());

			} else {
				if (val[1].equals("Product:")) {
					Product product = new Product();
					product.setName(val[2]);
					Product p = productBll.findProductByName(product.getName());
					productBll.deleteProduct(p, p.getName());
				}
			}
		}
	}

	int nr_bills = 0;

	/**
	 * @param val String ce contine pe rand cate o linie din fisierul text
	 * @throws DocumentException     exceptie de tip document
	 * @throws FileNotFoundException exceptie de tip file
	 */
	public void order(String[] val) throws DocumentException, FileNotFoundException {

		if (val[0].equals("Order:")) {
			nr_bills++;
			Bill bill = new Bill();
			String[] n = val[2].split(",");
			bill.setClientName(val[1] + " " + n[0]);
			n = val[3].split(",");
			bill.setProductName(n[0]);
			bill.setQuantity(Integer.parseInt(val[4]));

			String file_name = "Bills" + nr_bills + ".pdf";
			Document documentBills = new Document();
			PdfWriter.getInstance(documentBills, new FileOutputStream(file_name));
			documentBills.open();

			double ok = productBll.verificare(n[0], Integer.parseInt(val[4]));
			if (ok == -1 || ok == -2) {
				documentBills.add(new Phrase("Stoc insuficient/Nu exista produsul"));
			} else {
				Product p = productBll.findProductByName(bill.getProductName());
				double total = bill.getQuantity() * p.getPrice();
				bill.setTotal(total);
				bill.setId(billBll.insertBill(bill));
				billBll.write(bill, documentBills);
			}
			documentBills.close();
		}
	}

	int nr_reports_clients = 0;
	int nr_reports_products = 0;
	int nr_reports_orders = 0;

	/**
	 * @param val String ce contine pe rand cate o linie din fisierul text
	 * @throws FileNotFoundException exceptie de tip file
	 * @throws DocumentException     exceptie de tip document
	 */
	public void report(String[] val) throws FileNotFoundException, DocumentException {

		if (val[0].equals("Report")) {
			if (val[1].equals("client")) {
				nr_reports_clients++;
				String file_name = "reportClients" + nr_reports_clients + ".pdf";
				Document documentClienti = new Document();
				PdfWriter.getInstance(documentClienti, new FileOutputStream(file_name));
				documentClienti.open();
				List<Client> listaClienti = clientBll.findAll();
				clientBll.writeInPdf(listaClienti, documentClienti);
				documentClienti.close();
			}
		}
		if (val[1].equals("product")) {
			nr_reports_products++;
			String file_name1 = "reportProducts" + nr_reports_products + ".pdf";
			Document documentProducts = new Document();
			PdfWriter.getInstance(documentProducts, new FileOutputStream(file_name1));
			documentProducts.open();
			List<Product> listaProducts = productBll.findAll();
			productBll.writeInPdf(listaProducts, documentProducts);
			documentProducts.close();
		}
		if (val[1].equals("order")) {
			nr_reports_orders++;
			String file_name2 = "reportOrders" + nr_reports_orders + ".pdf";
			Document documentOrders = new Document();
			PdfWriter.getInstance(documentOrders, new FileOutputStream(file_name2));
			documentOrders.open();
			List<Bill> listaBills = billBll.findAll();
			billBll.writeOrder(listaBills, documentOrders);
			documentOrders.close();
		}
	}

	/**
	 * Citeste datele din fisierul de intrare si apeleaza metodele pentru inserare,
	 * stergere, order si report
	 * 
	 * @throws FileNotFoundException exceptie de tip file
	 * @throws DocumentException     exceptie de tip document
	 */
	public void read() throws FileNotFoundException, DocumentException {

		while (scanner.hasNextLine()) {
			String a = scanner.nextLine();
			String[] val = a.split(" ");
			insert(val);
			delete(val);
			order(val);
			report(val);
		}
	}

	/**
	 * Inchide fisierul de intrare
	 */
	public void close() {
		scanner.close();
	}

	public Scanner getScanner() {
		return scanner;
	}

	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
}