package com.tetris1.game.tetris.model.beans;

public class Player {
	
	public Integer getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLines() {
		return lines;
	}
	public void setLines(Integer lines) {
		this.lines = lines;
	}
	public Integer getPoIntegers() {
		return poIntegers;
	}
	public void setPoIntegers(Integer poIntegers) {
		this.poIntegers = poIntegers;
	}
	public Integer getLevels() {
		return levels;
	}
	public void setLevels(Integer levels) {
		this.levels = levels;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer playerId;
	public String name;
	public Integer lines;
	public Integer poIntegers;
	public Integer levels;
	private String password;

}
