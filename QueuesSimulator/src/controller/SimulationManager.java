package controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import model.Scheduler;
import model.Task;

public class SimulationManager implements Runnable {

	private Queue<Task> q = new LinkedList<Task>();
	private Scheduler sch = new Scheduler();
	private ReadWrite r = new ReadWrite();

	private int timeLimit = 40;
	private boolean waitingTasks = true;
	private boolean empty = false;
	private double averageTime;

	public SimulationManager(Queue<Task> q, Scheduler sch, ReadWrite r) {
		this.q = q;
		this.sch = sch;
		this.r = r;
	}

	public void print() {

		for (Task c : q) {
			c.print();
		}
	}

	@Override
	public void run() {
		int currentTime = 0;
		while (currentTime < timeLimit && (waitingTasks == true || empty == false)) {
			try {
				for (Iterator<Task> itr = q.iterator(); itr.hasNext();) {
					Task task = itr.next();
					if (task.getArrivalTime() == currentTime) {
						sch.searchServer(task);
						sch.timeAvgServer(task);
						itr.remove();
					}
				}
				for (int i = 0; i < r.getNoQueues(); i++) {
					Thread t = new Thread(sch.getServers().get(i));
					t.start();
				}
				String s = new String("Time " + currentTime);
				try {
					r.writing(s);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				String s1 = new String("\nWaiting clients: ");
				try {
					r.writing(s1);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				waitingTasks = false;
				for (Task task : q) {
					if (task.getArrivalTime() > currentTime) {
						task.print();
						waitingTasks = true;
					}
				}
				if (waitingTasks == false) {
					String s2 = new String("none");
					try {
						r.writing(s2);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
				sch.print();
				empty = sch.emptyScheduler();
				currentTime++;
				Thread.sleep(600);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		String s3 = new String("Average time = " + sch.time());
		try {
			r.writing(s3);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public double getAverageTime() {
		return averageTime;
	}

	public void setAverageTime(double averageTime) {
		this.averageTime = averageTime;
	}

	public boolean isWaitingTasks() {
		return waitingTasks;
	}

	public void setWaitingTasks(boolean waitingTasks) {
		this.waitingTasks = waitingTasks;
	}

	public ReadWrite getR() {
		return r;
	}

	public void setR(ReadWrite r) {
		this.r = r;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FileNotFoundException {

		PrintWriter pw = new PrintWriter(args[1]);
		pw.print("");
		pw.close();

		ReadWrite r = new ReadWrite();
		r.setInput(args[0]);
		r.setOutput(args[1]);
		r.open();
		r.read();

		Scheduler sch = new Scheduler();
		sch.getR().setInput(args[0]);
		sch.getR().setOutput(args[1]);
		sch.addServer(r);

		Queue<Task> q = new LinkedList<Task>();
		for (int i = 0; i < r.getNoTasks(); i++) {
			Task task = new Task();
			task.getR().setInput(args[0]);
			task.getR().setOutput(args[1]);
			task.generateRandomTask(r);
			q.add(task);
			Collections.sort((List<Task>) q);
		}
		int id = 0;
		for (Task task : q) {
			task.getR().setInput(args[0]);
			task.getR().setOutput(args[1]);
			id++;
			task.setId(id);
		}

		SimulationManager lista = new SimulationManager(q, sch, r);
		lista.setTimeLimit(r.getTimeSimulation());
		lista.run();
		r.close();
	}
}