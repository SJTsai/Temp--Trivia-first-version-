package com.pets.triviaforkids;

public class TFQuestion {
	
	private int mTFQuestion;
	private boolean mQuestionAnswer;

	public TFQuestion(int questionResId, boolean questionAnswer) {
		mTFQuestion = questionResId;
		mQuestionAnswer = questionAnswer;
	}

	public int getTFQuestion() {
		return mTFQuestion;
	}

	public void setTFQuestion(int questionResId) {
		mTFQuestion = questionResId;
	}

	public boolean isTrue() {
		return mQuestionAnswer;
	}

	public void setQuestionAnswer(boolean questionAnswer) {
		mQuestionAnswer = questionAnswer;
	}
}
