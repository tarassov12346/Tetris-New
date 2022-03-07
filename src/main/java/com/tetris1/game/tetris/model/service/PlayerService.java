package com.tetris1.game.tetris.model.service;

import com.tetris1.game.tetris.model.beans.Player;

public interface PlayerService {

	void playerLogin(String name, String password);
	void PlayerSignUp(Player player);
	Player getPlayerDetails(Integer playerId);
	void updatePlayerDetails(Player player);
	
}
