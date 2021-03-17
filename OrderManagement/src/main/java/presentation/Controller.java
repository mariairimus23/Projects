package presentation;

import java.io.FileNotFoundException;
import com.itextpdf.text.DocumentException;

/**
 * Date Apr 15-2020
 * Aceasta clasa contine metoda main cu o instanta de tip
 * Parser pentru fisier
 * 
 * @author Irimus Maria
 */
public class Controller {

	/**
	 * Metoda main care seteaza fisierul de intrare, il deschide, citeste din
	 * interiorul sau si mai apoi il inchide
	 * 
	 * @param args vector de argumente de tip String
	 * @throws FileNotFoundException exceptie de tip file
	 * @throws DocumentException     exceptie de tip document
	 */
	public static void main(String[] args) throws FileNotFoundException, DocumentException {

		Parser r = new Parser();
		r.setInput(args[0]);
		r.open();
		r.read();
		r.close();
	}
}