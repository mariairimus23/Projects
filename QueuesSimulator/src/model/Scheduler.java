package model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import controller.ReadWrite;

public class Scheduler {

	private List<Server> servers = new ArrayList<Server>();
	ReadWrite r = new ReadWrite();
	private int noTasks;
	private int procTasks;

	public Scheduler() {

	}

	public void addServer(ReadWrite r) {
		for (int i = 0; i < r.getNoQueues(); i++) {
			Server serv = new Server();
			servers.add(serv);
			setProcTasks(getProcTasks() + serv.getProcTasks());
		}
	}

	public void searchServer(Task task) {
		int minWaitingTime = Integer.MAX_VALUE;
		int indexServer = 0;
		int i = 0;

		for (Server c : servers) {
			if (minWaitingTime > c.getTotalTime()) {
				minWaitingTime = c.getTotalTime();
				indexServer = i;
			}
			i++;
		}
		servers.get(indexServer).addTasks(task);
		servers.get(indexServer).setClosed(false);
	}

	public void print() {

		int i = 0;
		for (Server server : servers) {
			String s = new String("\nQueue " + (i + 1) + ": ");
			try {
				r.writing(s);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			if (server.isClosed() == true) {
				String s1 = new String("closed");
				try {
					r.writing(s1);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			server.print();
			i++;
		}
		String s2 = new String("\n\n");
		try {
			r.writing(s2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean emptyScheduler() {

		int nrServers = 0;
		int nrServersClosed = 0;
		for (Server server : servers) {
			if (server.isClosed() == true) {
				nrServersClosed++;
			}
			nrServers++;
		}

		if (nrServers == nrServersClosed) {

			return true;
		} else
			return false;
	}

	private double timeAvg = 0;

	public void timeAvgServer(Task c) {
		timeAvg = timeAvg + c.getWaitingTime();
		noTasks = c.getNoTasks();
	}

	private double averageTime = 0;

	public double time() {
		averageTime = timeAvg;
		averageTime = averageTime / noTasks;
		return averageTime;
	}

	public List<Server> getServers() {
		return servers;
	}

	public void setServers(List<Server> servers) {
		this.servers = servers;
	}

	public double getTimeAvg() {
		return timeAvg;
	}

	public void setTimeAvg(double timeAvg) {
		this.timeAvg = timeAvg;
	}

	public int getProcTasks() {
		return procTasks;
	}

	public void setProcTasks(int procTasks) {
		this.procTasks = procTasks;
	}

	public int getNoTasks() {
		return noTasks;
	}

	public void setNoTasks(int noTasks) {
		this.noTasks = noTasks;
	}

	public ReadWrite getR() {
		return r;
	}

	public void setR(ReadWrite r) {
		this.r = r;
	}
}