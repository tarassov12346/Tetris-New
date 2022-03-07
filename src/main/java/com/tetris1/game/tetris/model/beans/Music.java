package com.tetris1.game.tetris.model.beans;

public class Music {
	
	public Integer getMusicId() {
		return musicId;
	}
	public void setMusicId(Integer musicId) {
		this.musicId = musicId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getStressLevel() {
		return stressLevel;
	}
	public void setStressLevel(Integer stressLevel) {
		this.stressLevel = stressLevel;
	}
	public Integer musicId;
	public String fileName;
	public Integer stressLevel;

}
