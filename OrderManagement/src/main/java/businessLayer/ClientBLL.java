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
import dataAccessLayer.ClientDAO;
import model.Client;

/**
 * Date Apr 15-2020 
 * Aceasta clasa descrie obiectul ClientBll si apeleaza
 * metodele din clasa ClientDAO, implementeaza logica aplicatiei
 * 
 * @author Irimus Maria
 */
public class ClientBLL {

	private ClientDAO clientDAO = new ClientDAO();

	public ClientBLL() {
	}

	/**
	 * Gaseste clientul dupa nume
	 * 
	 * @param name numele clientului cautat
	 * @return clientul gasit
	 */
	public Client findClientByName(String name) {
		Client client = ClientDAO.findByName(name);
		if (client == null) {
			throw new NoSuchElementException("The client with name =" + name + " was not found!");
		}
		return client;
	}

	/**
	 * Insereaza un client
	 * 
	 * @param client ce trebuie inserat
	 * @return id-ul clientului inserat
	 */
	public int insertClient(Client client) {

		return ClientDAO.insert(client);
	}

	/**
	 * Actualizeaza datele unui client
	 * 
	 * @param client ce trebuie actualizat
	 */
	public void updateClient(Client client) {

		ClientDAO.update(client);
	}

	/**
	 * Sterge din tabela un client
	 * 
	 * @param client     ce trebuie sters
	 * @param clientName numele dupa care este cautat clientul
	 * @return id-ul clientului sters
	 */
	public String deleteClient(Client client, String clientName) {

		return ClientDAO.delete(client, clientName);
	}

	/**
	 * Gaseste toti clientii din tabela
	 * 
	 * @return lista tuturor clientilor din tabela
	 */
	public List<Client> findAll() {

		return clientDAO.findAll();
	}

	/**
	 * Scrierea datelor din mySQL in documentul de tip pdf
	 * 
	 * @param clients  lista de clienti din tabela
	 * @param document documentul in care se vor scrie datele
	 * @throws DocumentException exceptie de tip document
	 */
	public void writeInPdf(List<Client> clients, Document document) throws DocumentException {

		Paragraph paragraph = new Paragraph("Client report\n\n");
		document.add(paragraph);

		PdfPTable table = new PdfPTable(2);
		PdfPCell header1 = new PdfPCell(new Phrase("Name"));
		header1.setBackgroundColor(BaseColor.ORANGE);
		header1.setBorderWidth(2);
		table.addCell(header1);
		PdfPCell header2 = new PdfPCell(new Phrase("Adress"));
		header2.setBackgroundColor(BaseColor.ORANGE);
		header2.setBorderWidth(2);
		table.addCell(header2);

		for (int i = 0; i < clients.size(); i++) {

			table.addCell(clients.get(i).getName());
			table.addCell(clients.get(i).getAddress());
		}

		document.add(table);
		document.close();
		System.out.println("Client report is finished");
	}

}