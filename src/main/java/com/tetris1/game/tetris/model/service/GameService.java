package com.tetris1.game.tetris.model.service;

import com.tetris1.game.tetris.model.beans.GameZone;

public interface GameService {
	
	void insertGameDetails(GameZone game);
	void updateGameDetails(GameZone game);
	GameZone getCurrentGameDetails(Integer playerId);

}
