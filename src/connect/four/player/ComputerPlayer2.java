package connect.four.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import connect.four.Game;
import connect.four.board.Board;
import connect.four.board.ReadWritableBoard;
import connect.four.board.ReadableBoard;

/**
 * AI implementation of Player, identified by its
 * {@link #performPlay(ReadWritableBoard)} algorithm, which selects a column in
 * which to make its move by considering the possibilities for future moves down
 * to a certain depth.
 * 
 * @see connect.four.player.Player
 * 
 */
public class ComputerPlayer2 implements Player
{
	private static final int MAX_BOARD_SCORE = 100000;
	private static final int MIN_BOARD_SCORE = -100000;
	
	/**
	 * How many moves this AI will think ahead, when determining its next move.
	 */
	int depth;
	
	/**
	 * Constructs a ComputerPlayer object with the specified depth
	 * 
	 * @param depth
	 */
	public ComputerPlayer2(int depth)
	{
		this.depth = depth;
	}
	
	/**
	 * Constructs a ComputerPlayer object with the default depth (6)
	 * 
	 * @see #ComputerPlayer(int)
	 */
	public ComputerPlayer2()
	{
		this(1);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see connect.four.player.Player#getName()
	 */
	@Override
	public String getName()
	{
		return "Computer";
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * connect.four.player.Player#performPlay(connect.four.board.ReadWritableBoard
	 * )
	 */
	@Override
	public void performPlay(ReadWritableBoard board)
	{
		try
		{
			board.play(
					calculateBestMove(board, depth, this, getOpponent(board)),
					this);
		}
		catch (IllegalStateException e)
		{
			board.play((new Random()).nextInt(board.getWidth()), this);
		}
	}
	
	private int calculateBestMove(ReadWritableBoard board, int depth,
			Player player, Player opponent)
	{
		int width = board.getWidth();
		int bestMove;
		
		if (board.getMoveCount() == 0)
		{
			Random random = (new Random());
			bestMove = random.nextInt(width);
		}
		else if (board.isFull())
		{
			bestMove = -1;
		}
		else
		{
			int bestScore = Integer.MIN_VALUE;
			bestMove = -1;
			
			for (int columnIndex = 0; columnIndex < width; columnIndex++)
			{
				if (board.isColumnFull(columnIndex))
				{
					System.out.println("column full");
					continue;
				}
				else
				{
					System.out.println("eval: " + columnIndex);
					int moveScore = scoreMove(columnIndex, depth, board,
							player, opponent);

					/*System.out.println("move score: " + moveScore);
					System.out.println("best score: " + bestScore);*/
					
					if (moveScore > bestScore)
					{
						bestScore = moveScore;
						bestMove = columnIndex;
					}
						
				}
			}
		}
		
		return bestMove;
	}
	
	/**
	 * Recursively determines the best (locally - see depth) score, by
	 * determining the possibility of the player or the opponent winning in x
	 * number of turns, up to *depth*.
	 * 
	 * Moves that result in a quick victory for the player, and/or an elongated
	 * victory for the opponent are favoured.
	 * 
	 * @param x
	 *            Move to consider
	 * @param depth
	 *            How many moves in the future to consider
	 * @param board
	 *            Which board to consider playing on
	 * @param opponent
	 *            Opponent player
	 * @return Score indicative of how good the specified move is.
	 */
	private int scoreMove(int x, int depth, ReadWritableBoard board,
			Player player, Player opponent)
	{
		final String illegalArgumentConditions = "Opponent and/or Player not on board";
		
		if (board.isFull())
			return 0;
		else if (depth < 1)
			throw new IllegalArgumentException("depth must be > 0");
		
		Board myMove = new Board(board);
		myMove.play(x, player);
		Player winner = Game.detectWinner(myMove, 4);
		
		if (winner == player)
			return MAX_BOARD_SCORE;
		else if (winner == opponent)
			return MIN_BOARD_SCORE;
		else if (board.isFull())
			return 0;
		else if (winner == null)
		{
			int score;
			System.out.println("score move: " + x);
			if (depth > 1)
			{
				System.out.println("depth+");
				score = (-scoreMove(0, depth - 1, myMove, opponent, player));
				for (int move = 1; move < board.getWidth(); move++)
				{
					int moveScore = (-scoreMove(move, depth - 1, myMove,
							opponent, player));
					if (moveScore < score)
						score = moveScore;
				}
			}
			else
			{
				System.out.println("depth = 1");
				score = (scoreBoard(myMove, player, opponent));
				System.out.println("score: " + score);
				for (int move = 1; move < myMove.getWidth(); move++)
				{
					int moveScore = (scoreBoard(myMove, player, opponent));
					if (moveScore > score)
						score = moveScore;
				}
			}
			
			return score;
		}
		else
			throw new IllegalArgumentException(illegalArgumentConditions);
	}
	
	private int scoreBoard(ReadWritableBoard board, Player player,
			Player opponent)
	{
		return scoreBoardObjectively(board, player)
				- scoreBoardObjectively(board, opponent);
	}
	
	private int scoreBoardObjectively(ReadWritableBoard board, Player player)
	{
		final class BoardSubSequence
		{
			boolean isFilled;
			int count;
		}
		
		final class PricedItem
		{
			int payoff;
			int cost;
		}
		
		final class BoardSequence
		{
			ArrayList<Integer> pieces = new ArrayList<>();
			ArrayList<Integer> gaps = new ArrayList<>();
			
			int potential()
			{
				return numPieces() + gaps();
			}
			
			int gaps()
			{
				return gaps.size();
			}
			
			int numPieces()
			{
				return pieces.size();
			}
			
			boolean isValid()
			{
				return (numPieces() > 0) && (potential() >= 4);
			}
			
			int getOffset()
			{
				int min = Integer.MAX_VALUE;
				for (Integer i : pieces)
				{
					if (i < min)
						min = i;
				}
				
				for (Integer i : gaps)
				{
					if (i < min)
						min = i;
				}
				
				return min;
			}
			
			boolean[] getContinuousSequence()
			{
				boolean[] sequence = new boolean[potential()];
				int offset = getOffset();
				
				for (Integer i : pieces)
				{
					sequence[i - offset] = true;
				}
				
				for (Integer i : gaps)
				{
					sequence[i - offset] = false;
				}
				
				return sequence;
			}
			
			ArrayList<BoardSubSequence> getInterpretation()
			{
				boolean[] sequence = getContinuousSequence();
				
				ArrayList<BoardSubSequence> interpretation = new ArrayList<>();
				boolean initialized = false;
				BoardSubSequence ss = null;
				
				for (boolean isFilled : sequence)
				{
					if (!initialized)
					{
						ss = new BoardSubSequence();
						ss.isFilled = isFilled;
						initialized = true;
					}
					
					if (ss.isFilled == isFilled)
					{
						ss.count++;
					}
					else
					{
						interpretation.add(ss);
						initialized = false;
					}
				}
				
				return interpretation;
			}
			
			int movesUntilWin()
			{
				ArrayList<BoardSubSequence> interpretation = getInterpretation();
				
				if (isWin(interpretation))
					return 0;
				
				ArrayList<PricedItem> merges;
				int minMoves = Integer.MAX_VALUE;
				
				for (int mergeSize = 2; mergeSize <= interpretation.size(); mergeSize++)
				{
					int extra = mergeSize - 1;
					int maxIndex = interpretation.size() - extra;
					merges = new ArrayList<>();
					for (int index = 0; index < maxIndex; index += extra)
					{
						PricedItem merge = new PricedItem();
						
						for (int index2 = index; index2 < index + extra; index2++)
						{
							BoardSubSequence item = interpretation.get(index2);
							item.count++;
							if (item.isFilled)
								merge.payoff += item.count;
							else
								merge.cost += item.count;
						}
					}
					
					for (PricedItem item : merges)
					{
						if ((item.payoff + item.cost) >= 4)
						{
							if (item.cost < minMoves)
								minMoves = item.cost;
						}
					}
				}
				
				return minMoves;
			}
			
			boolean isWin(ArrayList<BoardSubSequence> interpretation)
			{
				if ((numPieces() == 4) && (gaps() == 0))
					return true;
				else
				{
					for (BoardSubSequence s : interpretation)
					{
						if (s.isFilled && (s.count >= 4))
							return true;
					}
					
					return false;
				}
			}
			
			boolean isWin()
			{
				if ((numPieces() == 4) && (gaps() == 0))
					return true;
				else
					return isWin(getInterpretation());
			}
			
			int evaluate()
			{
				return (MAX_BOARD_SCORE / ((int) Math.pow(4, movesUntilWin())));
			}
			
			public String toString()
			{
				return "{" + gaps() + "; " + numPieces() + "}";
			}
		}
		
		LinkedList<BoardSequence> columnSequences = new LinkedList<>();
		LinkedList<BoardSequence> rowSequences = new LinkedList<>();
		LinkedList<BoardSequence> diagonalSequences = new LinkedList<>();
		
		int width = board.getWidth();
		int height = board.getHeight();
		
		// Evaluate columns
		for (int column = 0; column < width; column++)
		{
			BoardSequence sequence = new BoardSequence();
			
			for (int row = 0; row < height; row++)
			{
				Player owner = board.whoPlayed(column, row);
				
				if ((owner == null))
					sequence.gaps.add(row);
				else if (owner == player)
					sequence.pieces.add(row);
				else if (owner != player)
				{
					if (sequence.isValid())
						columnSequences.add(sequence);
					
					sequence = new BoardSequence();
				}
				
				// Check if Player wins
				if (sequence.isWin())
					return MAX_BOARD_SCORE;
			}
			
			if (sequence.isValid())
				columnSequences.add(sequence);
		}
		
		// Evaluate rows
		for (int row = 0; row < height; row++)
		{
			BoardSequence sequence = new BoardSequence();
			
			for (int column = 0; column < width; column++)
			{
				Player owner = board.whoPlayed(column, row);
				
				if ((owner == null))
					sequence.gaps.add(column);
				else if (owner == player)
					sequence.pieces.add(column);
				else if (owner != player)
				{
					if (sequence.isValid())
						rowSequences.add(sequence);
					
					sequence = new BoardSequence();
				}
				
				// Check if Player wins
				if (sequence.isWin())
					return MAX_BOARD_SCORE;
			}
			
			if (sequence.isValid())
				rowSequences.add(sequence);
		}
		
		// Evaluate diagonal -down
		for (int row = 3; row < height; row++)
		{
			BoardSequence sequence = new BoardSequence();
			
			for (int column = 0; column <= row; column++)
			{
				Player owner = board.whoPlayed(column, row - column);
				
				if ((owner == null))
					sequence.gaps.add(column);
				else if (owner == player)
					sequence.pieces.add(column);
				else if (owner != player)
				{
					if (sequence.isValid())
						diagonalSequences.add(sequence);
					
					sequence = new BoardSequence();
				}
				
				// Check if Player wins
				if (sequence.isWin())
					return MAX_BOARD_SCORE;
			}
			
			if (sequence.isValid())
				diagonalSequences.add(sequence);
		}
		
		// Evaluate diagonal -up
		for (int row = 0; row < height - 3; row++)
		{
			BoardSequence sequence = new BoardSequence();
			
			for (int column = 0; column < height - row; column++)
			{
				Player owner = board.whoPlayed(column, row + column);
				
				if ((owner == null))
					sequence.gaps.add(column);
				else if (owner == player)
					sequence.pieces.add(column);
				else if (owner != player)
				{
					if (sequence.isValid())
						diagonalSequences.add(sequence);
					
					sequence = new BoardSequence();
				}
				
				// Check if Player wins
				if (sequence.isWin())
					return MAX_BOARD_SCORE;
			}
			
			if (sequence.isValid())
				diagonalSequences.add(sequence);
		}
		
		// Evaluate diagonals - right
		for (int column = 1; column < width - 3; column++)
		{
			BoardSequence sequence = new BoardSequence();
			
			for (int row = height - 1; row >= height - (width - column); row--)
			{
				Player owner = board.whoPlayed(column + height - row - 1, row);
				
				if ((owner == null))
					sequence.gaps.add(column);
				else if (owner == player)
					sequence.pieces.add(column);
				else if (owner != player)
				{
					if (sequence.isValid())
						diagonalSequences.add(sequence);
					
					sequence = new BoardSequence();
				}
				
				// Check if Player wins
				if (sequence.isWin())
					return MAX_BOARD_SCORE;
			}
			
			if (sequence.isValid())
				diagonalSequences.add(sequence);
		}
		
		// Evaluate diagonals - left
		for (int column = 1; column < width - 3; column++)
		{
			BoardSequence sequence = new BoardSequence();
			
			for (int row = 0; row < width - column; row++)
			{
				Player owner = board.whoPlayed(column + row, row);
				
				if ((owner == null))
					sequence.gaps.add(column);
				else if (owner == player)
					sequence.pieces.add(column);
				else if (owner != player)
				{
					if (sequence.isValid())
						diagonalSequences.add(sequence);
					
					sequence = new BoardSequence();
				}
				
				// Check if Player wins
				if (sequence.isWin())
					return MAX_BOARD_SCORE;
			}
			
			if (sequence.isValid())
				diagonalSequences.add(sequence);
		}
		
		// Evaluate sequences
		int columnScore = 0;
		int rowScore = 0;
		int diagonalScore = 0;
		System.out.println("col: " + Arrays.toString(columnSequences.toArray()));
		
		for (BoardSequence sequence : columnSequences)
			columnScore += sequence.evaluate();
		
		for (BoardSequence sequence : rowSequences)
			rowScore += sequence.evaluate();
		
		for (BoardSequence sequence : diagonalSequences)
			diagonalScore += sequence.evaluate();
		
		/*columnScore = columnScore / ((double) MAX_BOARD_SCORE);
		rowScore = rowScore / ((double) MAX_BOARD_SCORE);
		diagonalScore = diagonalScore / ((double) MAX_BOARD_SCORE);
		
		double scorePercent = (columnScore * 0.4) + (rowScore * 0.35)
				+ (diagonalScore * 0.25);
		int score = (int) (MAX_BOARD_SCORE * scorePercent);
		score = (score > MAX_BOARD_SCORE) ? MAX_BOARD_SCORE : score;*/
		int score = columnScore + rowScore + diagonalScore;
		score = (score > MAX_BOARD_SCORE) ? MAX_BOARD_SCORE : score;
		score = (score < MIN_BOARD_SCORE) ? MIN_BOARD_SCORE : score;
		return score;
	}
	
	/**
	 * Determines the opponent player based on the specified board. This method
	 * will search the specified board for a Player that is not this player
	 * object, and return the result.
	 * 
	 * Throws an Error if no opponent is found.
	 * 
	 * @param board
	 *            Board to consider
	 * @return The opponent player on the specified board
	 */
	private Player getOpponent(ReadableBoard board)
	{
		int l = board.getWidth();
		int m = board.getHeight();
		for (int i = 0; i != l; ++i)
		{
			for (int j = 0; j != m; ++j)
			{
				Player here = board.whoPlayed(i, j);
				if (here != null && here != this)
				{
					return here;
				}
			}
		}
		throw new IllegalStateException("Can't call getOpponent on first turn");
	}
	
}
