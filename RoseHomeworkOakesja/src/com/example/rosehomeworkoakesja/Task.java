package com.example.rosehomeworkoakesja;

import java.util.GregorianCalendar;

public class Task implements Comparable<Task> {

	private long mId;
	private String mName;
	private String mCourse;
	private GregorianCalendar mDueDate;
	public static final String[] MONTHS = { "JAN", "FEB", "MAR", "APR", "MAY",
			"JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };

	public Task(String name, String course, GregorianCalendar dueDate) {
		this.mName = name;
		this.mCourse = course;
		this.mDueDate = dueDate;
	}

	public long getId() {
		return mId;
	}

	public void setId(long id) {
		this.mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public String getCourse() {
		return mCourse;
	}

	public void setCourse(String course) {
		this.mCourse = course;
	}

	public GregorianCalendar getDueDate() {
		return mDueDate;
	}

	public void setDueDate(GregorianCalendar dueDate) {
		this.mDueDate = dueDate;
	}

	public void setDueDate(int day, int month, int year) {
		this.mDueDate = new GregorianCalendar();
		this.mDueDate.set(GregorianCalendar.DAY_OF_MONTH, day);
		this.mDueDate.set(GregorianCalendar.MONTH, month);
		this.mDueDate.set(GregorianCalendar.YEAR, year);
	}

	public int getYearDue() {
		return this.mDueDate.get(GregorianCalendar.YEAR);
	}

	public int getMonthDue() {
		return this.mDueDate.get(GregorianCalendar.MONTH);
	}

	public int getDayDue() {
		return this.mDueDate.get(GregorianCalendar.DAY_OF_MONTH);
	}

	@Override
	public int compareTo(Task another) {
		GregorianCalendar anotherDueDate = another.getDueDate();
		int comp = this.mDueDate.compareTo(anotherDueDate);
		return (comp == 0 ? this.mName.compareTo(another.getName()) : comp);
	}

	@Override
	public String toString() {
		return MONTHS[this.mDueDate.get(GregorianCalendar.MONTH)] + " "
				+ this.mDueDate.get(GregorianCalendar.DAY_OF_MONTH) + ", "
				+ this.mDueDate.get(GregorianCalendar.YEAR) + " " + mCourse + " "
				+ mName;
	}

}
