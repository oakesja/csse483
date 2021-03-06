package edu.rosehulman.sqlhighscores;

/**
 * Score model object includes name and score.
 */
public class Score implements Comparable<Score> {
	private String mName;
	private int mScore;
	private long mId;

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public int getScore() {
		return mScore;
	}

	public void setScore(int score) {
		mScore = score;
	}

	public long getId() {
		return mId;
	}

	public void setId(long id) {
		mId = id;
	}

	public int compareTo(Score other) {
		return other.getScore() - getScore();
	}

	public String toString() {
		return getName() + " " + getScore();
	}
}
