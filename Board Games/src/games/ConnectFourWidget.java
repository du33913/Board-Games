package games;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConnectFourWidget extends JPanel implements ActionListener, SpotListener{

	private enum Player {RED, BLACK};
	
	private JSpotBoard2 _board;
	private JLabel _message;
	private boolean _game_won;
	private Player _next_to_play;
	private Spot[] winners;
	
	public ConnectFourWidget() {
		_board = new JSpotBoard2(7, 6);
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
		_next_to_play = Player.RED;
		_message.setText("Welcome to Connect Four. Red to play");
	}

	@Override
	public void spotClicked(Spot spot) {
		// TODO Auto-generated method stub
		
		if (_game_won) {
			return;
		}
		boolean checker = false;
		for (int i = 0; i < 6; i++) {
			if (_board.getSpotAt(spot.getSpotX(), i).isEmpty()) {
				checker = true;
			}
		}
		if (!checker) {
			return;
		}
		
		for (int i = 0; i< 7; i++) {
			for (int j = 0; j < 6; j++) {
				_board.getSpotAt(i, j).unhighlightSpot();
			}
		}
		
		Spot actualSpot = spot;
		String player_name = null;
		String next_player_name = null;
		Color player_color = null;
		winners = new Spot[4];
		
		
		if (_next_to_play == Player.RED) {
			player_color = Color.RED;
			player_name = "Red";
			next_player_name = "Black";
			_next_to_play = Player.BLACK;
		} else {
			player_color = Color.BLACK;
			player_name = "Black";
			next_player_name = "Red";
			_next_to_play = Player.RED;
		}
		
		for (int i = 0; i < 6; i++) {
			if (_board.getSpotAt(spot.getSpotX(), 5 - i).isEmpty()) {
				_board.getSpotAt(spot.getSpotX(), 5 - i).setSpot();
				_board.getSpotAt(spot.getSpotX(), 5 - i).setSpotColor(player_color);
				actualSpot = _board.getSpotAt(spot.getSpotX(), 5 - i);
				break;
			}
		}
		
		winners[0] = actualSpot;
		
		// column
		int count = 1;
		if (actualSpot.getSpotY() < 3) {
			for (int i = 1; i < 4; i++) {
				if (_board.getSpotAt(actualSpot.getSpotX(), actualSpot.getSpotY() + i).getSpotColor() == player_color) {
					count++;
					winners[i] = _board.getSpotAt(actualSpot.getSpotX(), actualSpot.getSpotY() + i);
				} else {
					break;
				}
			}
			if (count == 4) {
				_game_won = true;
				for (int i = 0; i < 4; i++) {
					winners[i].highlightSpot();
				}
				_message.setText(player_name + " wins!");
				return;
			}
		}
		
		// row
		// right
		count = 1;
		for (int i = 1; i < 4; i++) {
			if ((actualSpot.getSpotX() + i) > 6 || (actualSpot.getSpotX() + i) < 0 || 
					(actualSpot.getSpotY()) < 0 || (actualSpot.getSpotY()) > 5) {
				break;
			}
			if (_board.getSpotAt(actualSpot.getSpotX() + i, actualSpot.getSpotY()).isEmpty()) {
				break;
			}
			if (_board.getSpotAt(actualSpot.getSpotX() + i, actualSpot.getSpotY()).getSpotColor() == player_color) {
				count++;
				winners[i] = _board.getSpotAt(actualSpot.getSpotX() + i, actualSpot.getSpotY());
			} else {
				break;
			}
		}
		if (count == 4) {
			_game_won = true;
			for (int i = 0; i < 3; i++) {
				winners[i].highlightSpot();
			}
			_message.setText(player_name + " wins!");
			return;
		}
		
		// left
		int store = count;
		for (int i = 1; i < (5 - store); i++) {
			if ((actualSpot.getSpotX() - i) > 6 || (actualSpot.getSpotX() - i) < 0 || 
					(actualSpot.getSpotY()) < 0 || (actualSpot.getSpotY()) > 5) {
				break;
			}
			if (_board.getSpotAt(actualSpot.getSpotX() - i, actualSpot.getSpotY()).isEmpty()) {
				break;
			}
			if (_board.getSpotAt(actualSpot.getSpotX() - i, actualSpot.getSpotY()).getSpotColor() == player_color) {
				count++;
				winners[count - 1] = _board.getSpotAt(actualSpot.getSpotX() - i, actualSpot.getSpotY());
			} else {
				break;
			}
		}
		if (count == 4) {
			_game_won = true;
			for (int i = 0; i < 3; i++) {
				winners[i].highlightSpot();
			}
			_message.setText(player_name + " wins!");
			return;
		}
		
		// diagonal
		// up
		count = 1;
		for (int i = 1; i < 4; i++) {
			if ((actualSpot.getSpotX() + i) > 6 || (actualSpot.getSpotX() + i) < 0 || 
					(actualSpot.getSpotY() - i) < 0 || (actualSpot.getSpotY() - i) > 5) {
				break;
			}
			if (_board.getSpotAt(actualSpot.getSpotX() + i, actualSpot.getSpotY() - i).isEmpty()) {
				break;
			}
			if (_board.getSpotAt(actualSpot.getSpotX() + i, actualSpot.getSpotY() - i).getSpotColor() == player_color) {
				count++;
				winners[i] = _board.getSpotAt(actualSpot.getSpotX() + i, actualSpot.getSpotY() - i);
			} else {
				break;
			}
		}
		if (count == 4) {
			_game_won = true;
			for (int i = 0; i < 3; i++) {
				winners[i].highlightSpot();
			}
			_message.setText(player_name + " wins!");
			return;
		}
		
		// down
		store = count;
		for (int i = 1; i < (5 - store); i++) {
			if ((actualSpot.getSpotX() - i) > 6 || (actualSpot.getSpotX() - i) < 0 || 
					(actualSpot.getSpotY() + i) < 0 || (actualSpot.getSpotY() + i) > 5) {
				break;
			}
			if (_board.getSpotAt(actualSpot.getSpotX() - i, actualSpot.getSpotY() + i).isEmpty()) {
				break;
			}
			if (_board.getSpotAt(actualSpot.getSpotX() - i, actualSpot.getSpotY() + i).getSpotColor() == player_color) {
				count++;
				winners[count - 1] = _board.getSpotAt(actualSpot.getSpotX() - i, actualSpot.getSpotY() + i);
			} else {
				break;
			}
		}
		if (count == 4) {
			_game_won = true;
			for (int i = 0; i < 3; i++) {
				winners[i].highlightSpot();
			}
			_message.setText(player_name + " wins!");
			return;
		}
		
		//anti-diagonal
		// down
		count = 1;
		for (int i = 1; i < 4; i++) {
			if ((actualSpot.getSpotX() + i) > 6 || (actualSpot.getSpotX() + i) < 0 || 
					(actualSpot.getSpotY() + i) < 0 || (actualSpot.getSpotY() + i) > 5) {
				break;
			}
			if (_board.getSpotAt(actualSpot.getSpotX() + i, actualSpot.getSpotY() + i).isEmpty()) {
				break;
			}
			if (_board.getSpotAt(actualSpot.getSpotX() + i, actualSpot.getSpotY() + i).getSpotColor() == player_color) {
				count++;
				winners[i] = _board.getSpotAt(actualSpot.getSpotX() + i, actualSpot.getSpotY() + i);
			} else {
				break;
			}
		}
		if (count == 4) {
			_game_won = true;
			for (int i = 0; i < 3; i++) {
				winners[i].highlightSpot();
			}
			_message.setText(player_name + " wins!");
			return;
		}
		
		// up
		store = count;
		for (int i = 1; i < (5 - store); i++) {
			if ((actualSpot.getSpotX() - i) > 6 || (actualSpot.getSpotX() - i) < 0 || 
					(actualSpot.getSpotY() - i) < 0 || (actualSpot.getSpotY() - i) > 5) {
				break;
			}
			if (_board.getSpotAt(actualSpot.getSpotX() - i, actualSpot.getSpotY() - i).isEmpty()) {
				break;
			}
			if (_board.getSpotAt(actualSpot.getSpotX() - i, actualSpot.getSpotY() - i).getSpotColor() == player_color) {
				count++;
				winners[count - 1] = _board.getSpotAt(actualSpot.getSpotX() - i, actualSpot.getSpotY() - i);
			} else {
				break;
			}
		}
		if (count == 4) {
			_game_won = true;
			for (int i = 0; i < 3; i++) {
				winners[i].highlightSpot();
			}
			_message.setText(player_name + " wins!");
			return;
		}
		
		if (!_game_won) {
			boolean check = false;
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 6; j++) {
					if (_board.getSpotAt(i, j).isEmpty()) {
						check = true;
					}
				}
			}
			if (!check) {
				_message.setText("Draw game.");
				return;
			}
		}
		
		if (!_game_won) {
			_message.setText(next_player_name + " to play");
		}
		

	}

	@Override
	public void spotEntered(Spot spot) {
		// TODO Auto-generated method stub
		if (_game_won) {
			return;
		} else {
			for (int i = 0; i < 6; i++) {
				if (_board.getSpotAt(spot.getSpotX(), i).isEmpty()) {
					_board.getSpotAt(spot.getSpotX(), i).highlightSpot();
				}
			}
		}
	}

	@Override
	public void spotExited(Spot spot) {
		// TODO Auto-generated method stub
			for (int i = 0; i < 6; i++) {
				_board.getSpotAt(spot.getSpotX(), i).unhighlightSpot();
			}
			
			if (_game_won) {
				for (int i = 0; i < 4; i++) {
					winners[i].highlightSpot();
				}
			}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		resetGame();
	}

}
