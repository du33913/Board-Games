package games;

import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class OthelloWidget extends JPanel implements ActionListener, SpotListener {

private enum Player {BLACK, WHITE};
	
	private JSpotBoard3 _board;
	private JLabel _message;
	private boolean _game_won;
	private Player _next_to_play;
	private Player opponent;
	private int whiteScore;
	private int blackScore;
	
	
	public OthelloWidget() {
		// TODO Auto-generated constructor stub
		
		_board = new JSpotBoard3(8, 8);
		_message = new JLabel();
		
		setLayout(new BorderLayout());
		add(_board, BorderLayout.CENTER);
		
		JPanel reset_message_panel = new JPanel();
		reset_message_panel.setLayout(new BorderLayout());
		
		JButton reset_button = new JButton("Restart");
		reset_button.addActionListener(this);
		reset_message_panel.add(reset_button, BorderLayout.EAST);
		reset_message_panel.add(_message, BorderLayout.CENTER);
		
		add(reset_message_panel, BorderLayout.SOUTH);
		
		_board.addSpotListener(this);
		
		resetGame();
	}
	
	private void resetGame() {
		for (Spot s : _board) {
			s.clearSpot();
			s.unhighlightSpot();
		}
		
		_board.getSpotAt(3, 3).setSpot();
		_board.getSpotAt(3, 3).setSpotColor(Color.WHITE);
		_board.getSpotAt(3, 4).setSpot();
		_board.getSpotAt(3, 4).setSpotColor(Color.BLACK);
		_board.getSpotAt(4, 3).setSpot();
		_board.getSpotAt(4, 3).setSpotColor(Color.BLACK);
		_board.getSpotAt(4, 4).setSpot();
		_board.getSpotAt(4, 4).setSpotColor(Color.WHITE);
		
		_game_won = false;
		_next_to_play = Player.BLACK;
		opponent = Player.WHITE;
		whiteScore = 0;
		blackScore = 0;
		_message.setText("Welcome to Othello. Black to play");
	}

	@Override
	public void spotClicked(Spot spot) {
		// TODO Auto-generated method stub
		if (_game_won) {
			return;
		}
		
		if (!spot.isEmpty()) {
			return;
		}
		
		if (!checkValid(spot, _next_to_play)) {
			return;
		}
		
		String player_name = null;
		String next_player_name = null;
		Color player_color = null;
		Color opponent_color = null;
		
		if (_next_to_play == Player.BLACK) {
			player_color = Color.BLACK;
			player_name = "Black";
			next_player_name = "White";
			opponent_color = Color.WHITE;
		} else {
			player_color = Color.WHITE;
			player_name = "White";
			next_player_name = "Black";
			opponent_color = Color.BLACK;
		}
		
		// column
		// up
		if (spot.getSpotY() - 1 >= 0) {
			if (_board.getSpotAt(spot.getSpotX(), spot.getSpotY() - 1).getSpotColor() == opponent_color) {
				for (int i = 2; i <= spot.getSpotY(); i++) {
					if (_board.getSpotAt(spot.getSpotX(), spot.getSpotY() - i).isEmpty()) {
						break;
					}
					if (_board.getSpotAt(spot.getSpotX(), spot.getSpotY() - i).getSpotColor() == player_color) {
						spot.setSpot();
						spot.setSpotColor(player_color);
						for (int k = 1; k < i; k++) {
							_board.getSpotAt(spot.getSpotX(), spot.getSpotY() - k).setSpot();
							_board.getSpotAt(spot.getSpotX(), spot.getSpotY() - k).setSpotColor(player_color);
						}
						break;
					}
				}
			}
		}

		// down
		if (spot.getSpotY() + 1 < 8) {
			if (_board.getSpotAt(spot.getSpotX(), spot.getSpotY() + 1).getSpotColor() == opponent_color) {
				for (int i = 2; i < (8 - spot.getSpotY()); i++) {
					if (_board.getSpotAt(spot.getSpotX(), spot.getSpotY() + i).isEmpty()) {
						break;
					}
					if (_board.getSpotAt(spot.getSpotX(), spot.getSpotY() + i).getSpotColor() == player_color) {
						spot.setSpot();
						spot.setSpotColor(player_color);
						for (int k = 1; k < i; k++) {
							_board.getSpotAt(spot.getSpotX(), spot.getSpotY() + k).setSpot();
							_board.getSpotAt(spot.getSpotX(), spot.getSpotY() + k).setSpotColor(player_color);
						}
						break;
					}
				}
			}
		}

		// row
		// left
		if (spot.getSpotX() - 1 >= 0) {
			if (_board.getSpotAt(spot.getSpotX() - 1, spot.getSpotY()).getSpotColor() == opponent_color) {
				for (int i = 2; i <= spot.getSpotX(); i++) {
					if (_board.getSpotAt(spot.getSpotX() - i, spot.getSpotY()).isEmpty()) {
						break;
					}
					if (_board.getSpotAt(spot.getSpotX() - i, spot.getSpotY()).getSpotColor() == player_color) {
						spot.setSpot();
						spot.setSpotColor(player_color);
						for (int k = 1; k < i; k++) {
							_board.getSpotAt(spot.getSpotX() - k, spot.getSpotY()).setSpot();
							_board.getSpotAt(spot.getSpotX() - k, spot.getSpotY()).setSpotColor(player_color);
						}
						break;
					}
				}
			}
		}

		// right
		if (spot.getSpotX() + 1 < 8) {
			if (_board.getSpotAt(spot.getSpotX() + 1, spot.getSpotY()).getSpotColor() == opponent_color) {
				for (int i = 2; i < (8 - spot.getSpotX()); i++) {
					if (_board.getSpotAt(spot.getSpotX() + i, spot.getSpotY()).isEmpty()) {
						break;
					}
					if (_board.getSpotAt(spot.getSpotX() + i, spot.getSpotY()).getSpotColor() == player_color) {
						spot.setSpot();
						spot.setSpotColor(player_color);
						for (int k = 1; k < i; k++) {
							_board.getSpotAt(spot.getSpotX() + k, spot.getSpotY()).setSpot();
							_board.getSpotAt(spot.getSpotX() + k, spot.getSpotY()).setSpotColor(player_color);
						}
						break;
					}
				}
			}
		}

		// diagonal
		//up
		int z = 2;
		if ((spot.getSpotX() + 1) < 8 && (spot.getSpotY() - 1) >= 0) {
			if (_board.getSpotAt(spot.getSpotX() + 1, spot.getSpotY() - 1).getSpotColor() == opponent_color) {
				while ((spot.getSpotX() + z) < 8 && (spot.getSpotY() - z) >= 0) {
					if (_board.getSpotAt(spot.getSpotX() + z, spot.getSpotY() - z).isEmpty()) {
						break;
					}
					if (_board.getSpotAt(spot.getSpotX() + z, spot.getSpotY() - z).getSpotColor() == player_color) {
						spot.setSpot();
						spot.setSpotColor(player_color);
						for (int k = 1; k < z; k++) {
							_board.getSpotAt(spot.getSpotX() + k, spot.getSpotY() - k).setSpot();
							_board.getSpotAt(spot.getSpotX() + k, spot.getSpotY() - k).setSpotColor(player_color);
						}
						break;
					}
					z++;
				}
			}
		}


		// down
		z = 2;
		if ((spot.getSpotX() - 1) >= 0 && (spot.getSpotY() + 1) < 8) {
			if (_board.getSpotAt(spot.getSpotX() - 1, spot.getSpotY() + 1).getSpotColor() == opponent_color) {
				while ((spot.getSpotX() - z) >= 0 && (spot.getSpotY() + z) < 8) {
					if (_board.getSpotAt(spot.getSpotX() - z, spot.getSpotY() + z).isEmpty()) {
						break;
					}
					if (_board.getSpotAt(spot.getSpotX() - z, spot.getSpotY() + z).getSpotColor() == player_color) {
						spot.setSpot();
						spot.setSpotColor(player_color);
						for (int k = 1; k < z; k++) {
							_board.getSpotAt(spot.getSpotX() - k, spot.getSpotY() + k).setSpot();
							_board.getSpotAt(spot.getSpotX() - k, spot.getSpotY() + k).setSpotColor(player_color);
						}
						break;
					}
					z++;
				}
			}
		}

		// anti-diagonal
		// up
		z = 2;
		if ((spot.getSpotX() - 1) >= 0 && (spot.getSpotY() - 1) >= 0) {
			if (_board.getSpotAt(spot.getSpotX() - 1, spot.getSpotY() - 1).getSpotColor() == opponent_color) {
				while ((spot.getSpotX() - z) >= 0 && (spot.getSpotY() - z) >= 0) {
					if (_board.getSpotAt(spot.getSpotX() - z, spot.getSpotY() - z).isEmpty()) {
						break;
					}
					if (_board.getSpotAt(spot.getSpotX() - z, spot.getSpotY() - z).getSpotColor() == player_color) {
						spot.setSpot();
						spot.setSpotColor(player_color);
						for (int k = 1; k < z; k++) {
							_board.getSpotAt(spot.getSpotX() - k, spot.getSpotY() - k).setSpot();
							_board.getSpotAt(spot.getSpotX() - k, spot.getSpotY() - k).setSpotColor(player_color);
						}
						break;
					}
					z++;
				}
			}
		}

		//down
		z = 2;
		if ((spot.getSpotX() + 1) < 8 && (spot.getSpotY() + 1) < 8) {
			if (_board.getSpotAt(spot.getSpotX() + 1, spot.getSpotY() + 1).getSpotColor() == opponent_color) {
				while ((spot.getSpotX() + z) < 8 && (spot.getSpotY() + z) < 8) {
					if (_board.getSpotAt(spot.getSpotX() + z, spot.getSpotY() + z).isEmpty()) {
						break;
					}
					if (_board.getSpotAt(spot.getSpotX() + z, spot.getSpotY() + z).getSpotColor() == player_color) {
						spot.setSpot();
						spot.setSpotColor(player_color);
						for (int k = 1; k < z; k++) {
							_board.getSpotAt(spot.getSpotX() + k, spot.getSpotY() + k).setSpot();
							_board.getSpotAt(spot.getSpotX() + k, spot.getSpotY() + k).setSpotColor(player_color);
						}
						break;
					}
					z++;
				}
			}
		}
		
		
		if (checkLegitMove(opponent)) {
			if (_next_to_play == Player.BLACK) {
				_next_to_play = Player.WHITE;
				opponent = Player.BLACK;
			} else {
				_next_to_play = Player.BLACK;
				opponent = Player.WHITE;
			}
			_message.setText(next_player_name + " to play");
		}
		
		if (!checkLegitMove(opponent) && !checkLegitMove(_next_to_play)) {
			_game_won = true;
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (_board.getSpotAt(i, j).getSpotColor() == Color.WHITE) {
						whiteScore++;
					} else if (_board.getSpotAt(i, j).getSpotColor() == Color.BLACK) {
						blackScore++;
					}
				}
			}
			
			if (whiteScore > blackScore) {
				_message.setText("Game over. White wins. Score: " + whiteScore + " to " + blackScore);
			} else if (blackScore > whiteScore) {
				_message.setText("Game over. Black wins. Score: " + blackScore + " to " + whiteScore);
			} else {
				_message.setText("Game over. Draw game");
			}
		}
		
	}

	@Override
	public void spotEntered(Spot spot) {
		// TODO Auto-generated method stub
		if (_game_won) {
			return;
		}
		if (!spot.isEmpty()) {
			return;
		}
		if (checkValid(spot, _next_to_play)) {
			spot.highlightSpot();
		}
		
		
	}

	@Override
	public void spotExited(Spot spot) {
		// TODO Auto-generated method stub
		if (_game_won) {
			return;
		} else {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					_board.getSpotAt(i, j).unhighlightSpot();
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		resetGame();
	}
	
	public boolean checkValid(Spot spot, Player player) {
		
		Color player_color = null;
		Color opponent_color = null;
		
		if (player == Player.BLACK) {
			player_color = Color.BLACK;
			opponent_color = Color.WHITE;
		} else {
			player_color = Color.WHITE;
			opponent_color = Color.BLACK;
		}
		
		// column
		// up
		if (spot.getSpotY() - 1 >= 0) {
			if (_board.getSpotAt(spot.getSpotX(), spot.getSpotY() - 1).getSpotColor() == opponent_color) {
				for (int i = 2; i <= spot.getSpotY(); i++) {
					if (_board.getSpotAt(spot.getSpotX(), spot.getSpotY() - i).isEmpty()) {
						break;
					}
					if (_board.getSpotAt(spot.getSpotX(), spot.getSpotY() - i).getSpotColor() == player_color) {
						return true;
					}
				}
			}
		}

		// down
		if (spot.getSpotY() + 1 < 8) {
			if (_board.getSpotAt(spot.getSpotX(), spot.getSpotY() + 1).getSpotColor() == opponent_color) {
				for (int i = 2; i < (8 - spot.getSpotY()); i++) {
					if (_board.getSpotAt(spot.getSpotX(), spot.getSpotY() + i).isEmpty()) {
						break;
					}
					if (_board.getSpotAt(spot.getSpotX(), spot.getSpotY() + i).getSpotColor() == player_color) {
						return true;
					}
				}
			}
		}
		
		// row
		// left
		if (spot.getSpotX() - 1 >= 0) {
			if (_board.getSpotAt(spot.getSpotX() - 1, spot.getSpotY()).getSpotColor() == opponent_color) {
				for (int i = 2; i <= spot.getSpotX(); i++) {
					if (_board.getSpotAt(spot.getSpotX() - i, spot.getSpotY()).isEmpty()) {
						break;
					}
					if (_board.getSpotAt(spot.getSpotX() - i, spot.getSpotY()).getSpotColor() == player_color) {
						return true;
					}
				}
			}
		}
		
		// right
		if (spot.getSpotX() + 1 < 8) {
			if (_board.getSpotAt(spot.getSpotX() + 1, spot.getSpotY()).getSpotColor() == opponent_color) {
				for (int i = 2; i < (8 - spot.getSpotX()); i++) {
					if (_board.getSpotAt(spot.getSpotX() + i, spot.getSpotY()).isEmpty()) {
						break;
					}
					if (_board.getSpotAt(spot.getSpotX() + i, spot.getSpotY()).getSpotColor() == player_color) {
						return true;
					}
				}
			}
		}
		
		// diagonal
		//up
		int z = 2;
		if ((spot.getSpotX() + 1) < 8 && (spot.getSpotY() - 1) >= 0) {
			if (_board.getSpotAt(spot.getSpotX() + 1, spot.getSpotY() - 1).getSpotColor() == opponent_color) {
				while ((spot.getSpotX() + z) < 8 && (spot.getSpotY() - z) >= 0) {
					if (_board.getSpotAt(spot.getSpotX() + z, spot.getSpotY() - z).isEmpty()) {
						break;
					}
					if (_board.getSpotAt(spot.getSpotX() + z, spot.getSpotY() - z).getSpotColor() == player_color) {
						return true;
					}
					z++;
				}
			}
		}
		
		
		// down
		z = 2;
		if ((spot.getSpotX() - 1) >= 0 && (spot.getSpotY() + 1) < 8) {
			if (_board.getSpotAt(spot.getSpotX() - 1, spot.getSpotY() + 1).getSpotColor() == opponent_color) {
				while ((spot.getSpotX() - z) >= 0 && (spot.getSpotY() + z) < 8) {
					if (_board.getSpotAt(spot.getSpotX() - z, spot.getSpotY() + z).isEmpty()) {
						break;
					}
					if (_board.getSpotAt(spot.getSpotX() - z, spot.getSpotY() + z).getSpotColor() == player_color) {
						return true;
					}
					z++;
				}
			}
		}
		
		// anti-diagonal
		// up
		z = 2;
		if ((spot.getSpotX() - 1) >= 0 && (spot.getSpotY() - 1) >= 0) {
			if (_board.getSpotAt(spot.getSpotX() - 1, spot.getSpotY() - 1).getSpotColor() == opponent_color) {
				while ((spot.getSpotX() - z) >= 0 && (spot.getSpotY() - z) >= 0) {
					if (_board.getSpotAt(spot.getSpotX() - z, spot.getSpotY() - z).isEmpty()) {
						break;
					}
					if (_board.getSpotAt(spot.getSpotX() - z, spot.getSpotY() - z).getSpotColor() == player_color) {
						return true;
					}
					z++;
				}
			}
		}
		
		//down
		z = 2;
		if ((spot.getSpotX() + 1) < 8 && (spot.getSpotY() + 1) < 8) {
			if (_board.getSpotAt(spot.getSpotX() + 1, spot.getSpotY() + 1).getSpotColor() == opponent_color) {
				while ((spot.getSpotX() + z) < 8 && (spot.getSpotY() + z) < 8) {
					if (_board.getSpotAt(spot.getSpotX() + z, spot.getSpotY() + z).isEmpty()) {
						break;
					}
					if (_board.getSpotAt(spot.getSpotX() + z, spot.getSpotY() + z).getSpotColor() == player_color) {
						return true;
					}
					z++;
				}
			}
		}
		
		return false;
		
	}
	
	public boolean checkLegitMove(Player player) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (_board.getSpotAt(i, j).isEmpty()) {
					if (checkValid(_board.getSpotAt(i, j), player)) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
