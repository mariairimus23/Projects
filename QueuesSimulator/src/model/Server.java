package model;
import java.util.LinkedList;
import java.util.Queue;

public class Server implements Runnable {

	private Queue<Task> q = new LinkedList<Task>();
	private int totalTime = 0;
	boolean closed = true;
	public int procTasks = 0;

	public Server() {
		q = new LinkedList<Task>();
	}

	public void addTasks(Task task) {
		q.add(task);
		setTotalTime(getTotalTime() + task.getServiceTime());
		task.setWaitingTime(totalTime);
		setProcTasks(getProcTasks() + 1);
	}

	public void print() {
		for (Task task : q) {
			task.print();
		}
	}

	public Queue<Task> getQ() {
		return q;
	}

	public void setQ(Queue<Task> q) {
		this.q = q;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	@Override
	public void run() {

		if (!q.isEmpty()) {
			try {
				Thread.sleep(250);
				Task task = new Task();
				task = q.peek();
				task.setServiceTime(task.getServiceTime() - 1);
				this.totalTime--;
				if (task.getServiceTime() == 0) {
					q.remove(task);
				}
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		} else {
			setClosed(true);
		}
	}

	public int getProcTasks() {
		return procTasks;
	}

	public void setProcTasks(int procTasks) {
		this.procTasks = procTasks;
	}
}