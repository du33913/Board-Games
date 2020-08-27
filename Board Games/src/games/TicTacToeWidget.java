package games;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TicTacToeWidget extends JPanel implements ActionListener, SpotListener {

	private enum Player {BLACK, WHITE};
	
	private JSpotBoard1 _board;
	private JLabel _message;
	private boolean _game_won;
	private Player _next_to_play;
	
	public TicTacToeWidget() {
		// TODO Auto-generated constructor stub
		
		_board = new JSpotBoard1(3, 3);
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
		
		_game_won = false;
		_next_to_play = Player.WHITE;
		_message.setText("Welcome to Tic Tac Toe. White to play");
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
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				_board.getSpotAt(i, j).unhighlightSpot();
			}
		}
		
		String player_name = null;
		String next_player_name = null;
		Color player_color = null;
		
		if (_next_to_play == Player.WHITE) {
			player_color = Color.WHITE;
			player_name = "White";
			next_player_name = "Black";
			_next_to_play = Player.BLACK;
		} else {
			player_color = Color.BLACK;
			player_name = "Black";
			next_player_name = "White";
			_next_to_play = Player.WHITE;			
		}
		
		spot.setSpotColor(player_color);
		spot.setSpot();
		
		// column
		for (int i = 0; i < 3; i++) {
			if (_board.getSpotAt(spot.getSpotX(), i).isEmpty()) {
				break;
			}
			if (_board.getSpotAt(spot.getSpotX(), i).getSpotColor() != spot.getSpotColor()) {
				break;
			}
			if (i == 2) {
				_game_won = true;
			}
		}
		
		// row
		for (int i = 0; i < 3; i++) {
			if (_board.getSpotAt(i, spot.getSpotY()).isEmpty()) {
				break;
			}
			if (_board.getSpotAt(i, spot.getSpotY()).getSpotColor() != spot.getSpotColor()) {
				break;
			}
			if (i == 2) {
				_game_won = true;
			}
		}
		
		// diagonal
		if (spot.getSpotX() == spot.getSpotY()) {
			for (int i = 0; i < 3; i++) {
				if (_board.getSpotAt(i, i).isEmpty()) {
					break;
				}
				if (_board.getSpotAt(i, i).getSpotColor() != spot.getSpotColor()) {
					break;
				}
				if (i == 2) {
					_game_won = true;
				}
			}
		}
		
		// anti-diagonal
		if (spot.getSpotX() + spot.getSpotY() == 2) {
			for (int i = 0; i < 3; i++) {
				if (_board.getSpotAt(i, 2 - i).isEmpty()) {
					break;
				}
				if (_board.getSpotAt(i, 2 - i).getSpotColor() != spot.getSpotColor()) {
					break;
				}
				if (i == 2) {
					_game_won = true;
				}
			}
		}
		
		// draw
		if (!_game_won) {
			boolean checker = false;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (_board.getSpotAt(i, j).isEmpty()) {
						checker = true;
					}
				}
			}
			if (!checker) {
				_message.setText("Draw game.");
				return;
			}
		}
		
		if (_game_won) {
			_message.setText(player_name + " wins!");
		} else {
			_message.setText(next_player_name + " to play");
		}
		
	}

	@Override
	public void spotEntered(Spot spot) {
		// TODO Auto-generated method stub
		if (_game_won) {
			return;
		} else if (!spot.isEmpty()) {
			return;
		}
		spot.highlightSpot();
	}

	@Override
	public void spotExited(Spot spot) {
		// TODO Auto-generated method stub
		spot.unhighlightSpot();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
			/* Handles reset game button. Simply reset the game. */
					resetGame();
		
	}

}
