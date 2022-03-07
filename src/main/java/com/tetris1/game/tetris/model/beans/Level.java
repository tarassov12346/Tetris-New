package com.tetris1.game.tetris.model.beans;

public class Level {
	
	public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	public Integer getMusicId() {
		return musicId;
	}
	public void setMusicId(Integer musicId) {
		this.musicId = musicId;
	}
	public Integer getStressValue() {
		return stressValue;
	}
	public void setStressValue(Integer stressValue) {
		this.stressValue = stressValue;
	}
	public Integer levelId;
	public Integer speed;
	public Integer musicId;
	public Integer stressValue;

}
