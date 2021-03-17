package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ReadWrite {

	private Scanner scanner;
	private int noTasks;
	private int noQueues;
	private int timeSimulation;
	private int minArrivalTime;
	private int maxArrivalTime;
	private int minServiceTime;
	private int maxServiceTime;
	private String input;
	private String output;

	public ReadWrite() {

	}

	public void writing(String s1) throws FileNotFoundException {

		File file = new File(this.output);
		FileWriter fw = null;
		try {
			fw = new FileWriter(file, true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		pw = new PrintWriter(fw);
		pw.write(s1);
		pw.close();
	}

	public void open() {
		try {
			scanner = new Scanner(new File(this.input));
		} catch (Exception e) {
			System.out.println("could not find file");
		}
	}

	public void read() {

		String a = scanner.next();
		noTasks = Integer.parseInt(a);
		setNoTasks(noTasks);

		String b = scanner.next();
		noQueues = Integer.parseInt(b);
		setNoQueues(noQueues);

		String c = scanner.next();
		timeSimulation = Integer.parseInt(c);
		setTimeSimulation(timeSimulation);

		String d = scanner.next();
		String[] val1 = d.split(",");

		minArrivalTime = Integer.parseInt(val1[0]);
		maxArrivalTime = Integer.parseInt(val1[1]);

		String e = scanner.next();
		String[] val2 = e.split(",");

		minServiceTime = Integer.parseInt(val2[0]);
		maxServiceTime = Integer.parseInt(val2[1]);
	}

	public void close() {

		scanner.close();
	}

	public int getNoQueues() {
		return noQueues;
	}

	public void setNoQueues(int noQueues) {
		this.noQueues = noQueues;
	}

	public int getTimeSimulation() {
		return timeSimulation;
	}

	public void setTimeSimulation(int timeSimulation) {
		this.timeSimulation = timeSimulation;
	}

	public int getMinArrivalTime() {
		return minArrivalTime;
	}

	public void setMinArrivalTime(int minArrivalTime) {
		this.minArrivalTime = minArrivalTime;
	}

	public int getMaxArrivalTime() {
		return maxArrivalTime;
	}

	public void setMaxArrivalTime(int maxArrivalTime) {
		this.maxArrivalTime = maxArrivalTime;
	}

	public int getMinServiceTime() {
		return minServiceTime;
	}

	public void setMinServiceTime(int minServiceTime) {
		this.minServiceTime = minServiceTime;
	}

	public int getMaxServiceTime() {
		return maxServiceTime;
	}

	public void setMaxServiceTime(int maxServiceTime) {
		this.maxServiceTime = maxServiceTime;
	}

	public int getNoTasks() {
		return noTasks;
	}

	public void setNoTasks(int noTasks) {
		this.noTasks = noTasks;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public Scanner getScanner() {
		return scanner;
	}

	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}
}