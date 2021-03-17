package model;

import java.io.FileNotFoundException;

import controller.ReadWrite;

public class Task implements Comparable<Task> {

	private int id = 0;
	private int arrivalTime;
	private int serviceTime;
	private int waitingTime;
	private int noTasks;
	ReadWrite r = new ReadWrite();

	public Task() {
	}

	public void generateRandomTask(ReadWrite r) {

		noTasks = r.getNoTasks();
		setArrivalTime(
				r.getMinArrivalTime() + (int) (Math.random() * ((r.getMaxArrivalTime() - r.getMinArrivalTime()) + 1)));
		setServiceTime(
				r.getMinServiceTime() + (int) (Math.random() * ((r.getMaxServiceTime() - r.getMinServiceTime()) + 1)));
	}

	@Override
	public int compareTo(Task task) {

		if (arrivalTime == task.arrivalTime)
			return 0;
		else if (arrivalTime > task.arrivalTime)
			return 1;
		else
			return -1;
	}

	public void print() {

		String s1 = new String("(" + this.id + ", " + this.arrivalTime + ", " + this.serviceTime + "); ");
		try {
			r.writing(s1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
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