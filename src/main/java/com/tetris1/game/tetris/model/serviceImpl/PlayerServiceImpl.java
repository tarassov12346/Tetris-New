package com.tetris1.game.tetris.model.serviceImpl;

import com.tetris1.game.tetris.model.beans.Player;
import com.tetris1.game.tetris.model.service.PlayerService;

public class PlayerServiceImpl implements PlayerService {

	@Override
	public void playerLogin(String name, String password) {
		// authenticate player and return success or error code
	}

	@Override
	public void PlayerSignUp(Player player) {
		// add player details to db and return success/error response
	}

	@Override
	public Player getPlayerDetails(Integer playerId) {
		Player player = new Player();
		// get player details from db and return
		return player;
	}

	@Override
	public void updatePlayerDetails(Player player) {
		// update player details after each game and return succes or error code
		
	}

}
