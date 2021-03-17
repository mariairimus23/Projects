package businessLayer;

import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import dataAccessLayer.BillDAO;
import model.Bill;

/**
 * Date Apr 15-2020 
 * Aceasta clasa descrie obiectul BillBll si apeleaza metodele
 * din clasa BillDAO, implementeaza logica aplicatiei
 * 
 * @author Irimus Maria
 */
public class BillBLL {

	private BillDAO billDAO = new BillDAO();

	public BillBLL() {
	}

	/**
	 * Insereaza o comanda
	 * 
	 * @param bill ce trebuie inserat
	 * @return id-ul comenzii inserata
	 */
	public int insertBill(Bill bill) {
		return BillDAO.insert(bill);
	}

	/**
	 * Actualizeaza datele despre o comanda
	 * 
	 * @param bill ce trebuie actualizat
	 */
	public void updateBill(Bill bill) {
		BillDAO.update(bill);
	}

	/**
	 * Sterge din tabela o comanda
	 * 
	 * @param bill   ce trebuie sters
	 * @param billId id-ul dupa care este cautata comanda
	 * @return id-ul comenzii sterse
	 */
	public int deleteBill(Bill bill, int billId) {
		return BillDAO.delete(bill, billId);
	}

	/**
	 * Gaseste toate comenzile din tabela
	 * 
	 * @return lista tuturor comenzilor din tabela
	 */
	public List<Bill> findAll() {

		return billDAO.findAll();
	}

	/**
	 * Scrierea datelor din mySQL in documentul de tip pdf sub forma unei facturi
	 * 
	 * @param bill     comanda din tabela
	 * @param document documentul in care se vor scrie datele
	 * @throws DocumentException exceptie de tip document
	 */
	public void write(Bill bill, Document document) throws DocumentException {
		Paragraph paragraph = new Paragraph("Bill \n\n");
		document.add(paragraph);

		PdfPTable table = new PdfPTable(4);
		PdfPCell header1 = new PdfPCell(new Phrase("Client Name"));
		header1.setBackgroundColor(BaseColor.ORANGE);
		header1.setBorderWidth(2);
		table.addCell(header1);
		PdfPCell header2 = new PdfPCell(new Phrase("Product Name"));
		header2.setBackgroundColor(BaseColor.ORANGE);
		header2.setBorderWidth(2);
		table.addCell(header2);
		PdfPCell header3 = new PdfPCell(new Phrase("Quantity"));
		header3.setBackgroundColor(BaseColor.ORANGE);
		header3.setBorderWidth(2);
		table.addCell(header3);
		PdfPCell header4 = new PdfPCell(new Phrase("Total"));
		header4.setBackgroundColor(BaseColor.ORANGE);
		header4.setBorderWidth(2);
		table.addCell(header4);

		table.addCell(bill.getClientName());
		table.addCell(bill.getProductName());
		table.addCell(Integer.toString(bill.getQuantity()));
		table.addCell(Double.toString(bill.getTotal()));
		document.add(table);
		document.close();
		System.out.println("Bill is finished");
	}

	/**
	 * Scrierea datelor din mySQL in documentul de tip pdf
	 * 
	 * @param bills    lista de comenzi din tabela
	 * @param document documentul in care se vor scrie datele
	 * @throws DocumentException exceptie de tip document
	 */
	public void writeOrder(List<Bill> bills, Document document) throws DocumentException {

		Paragraph paragraph = new Paragraph("Order report\n\n");
		document.add(paragraph);

		PdfPTable table = new PdfPTable(3);
		PdfPCell header1 = new PdfPCell(new Phrase("Client Name"));
		header1.setBackgroundColor(BaseColor.ORANGE);
		header1.setBorderWidth(2);
		table.addCell(header1);
		PdfPCell header2 = new PdfPCell(new Phrase("Product Name"));
		header2.setBackgroundColor(BaseColor.ORANGE);
		header2.setBorderWidth(2);
		table.addCell(header2);
		PdfPCell header3 = new PdfPCell(new Phrase("Quantity"));
		header3.setBackgroundColor(BaseColor.ORANGE);
		header3.setBorderWidth(2);
		table.addCell(header3);

		for (int i = 0; i < bills.size(); i++) {

			table.addCell(bills.get(i).getClientName());
			table.addCell(bills.get(i).getProductName());
			table.addCell(Integer.toString(bills.get(i).getQuantity()));
		}

		document.add(table);
		document.close();
		System.out.println("Order report is finished");
	}
}