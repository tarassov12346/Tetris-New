package com.tetris1.game.tetris.model.beans;

import java.util.List;

public class GameZone {
	
	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getColumns() {
		return columns;
	}
	public void setColumns(Integer columns) {
		this.columns = columns;
	}
	public Integer getCurrentTetrimonyId() {
		return currentTetrimonyId;
	}
	public void setCurrentTetrimonyId(Integer currentTetrimonyId) {
		this.currentTetrimonyId = currentTetrimonyId;
	}
	public Integer getNextTetrimonyId() {
		return nextTetrimonyId;
	}
	public void setNextTetrimonyId(Integer nextTetrimonyId) {
		this.nextTetrimonyId = nextTetrimonyId;
	}
	public List<Integer> getHoldTetrymonyId() {
		return holdTetrymonyId;
	}
	public void setHoldTetrymonyId(List<Integer> holdTetrymonyId) {
		this.holdTetrymonyId = holdTetrymonyId;
	}
	public Integer getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}
	public Boolean getFilled() {
		return filled;
	}
	public void setFilled(Boolean filled) {
		this.filled = filled;
	}
	public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	private Integer gameId;
	private Integer rows;
	private Integer columns;
	private Integer currentTetrimonyId;
	private Integer nextTetrimonyId;
	private List<Integer> holdTetrymonyId;
	private Integer playerId;
	private Boolean filled;
	private Integer levelId;

}
