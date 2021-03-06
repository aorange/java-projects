import java.util.*;

/**
 * Game object that runs each game, contains all the players, their hands, and the deck state
 * @author Allen Tang
 */
public class Game {

    private static final int CARDS_FOR_A_BOOK = 4;

    private ArrayList<PlayerStrategy> players = new ArrayList<>();
    private Hand [] playerHands;
    private Deck gameDeck;
    private int [] numBooksForPlayer;
    private int currentPlayer;


    public ArrayList<PlayerStrategy> getPlayers() {
        return players;
    }

    public Hand[] getPlayerHands() {
        return playerHands;
    }

    public Deck getGameDeck() {
        return gameDeck;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int[] getNumBooksForPlayer() {
        return numBooksForPlayer;
    }



    /**
     * Creates the player hands, the deck, the array for holding number of books, and the player list
     * @param playerTypes the int array with the corresponding player type for each player number
     */
    public Game(int [] playerTypes){

        for(int i = 0; i < playerTypes.length; i++){
            if(playerTypes[i] == 0){
                NaivePlayerStrategy newPlayer = new NaivePlayerStrategy();
                newPlayer.initialize(i, playerTypes.length);
                players.add(newPlayer);
            } else{
                SmartPlayerStrategy newPlayer = new SmartPlayerStrategy();
                newPlayer.initialize(i, playerTypes.length);
                players.add(newPlayer);
            }
        }

        gameDeck = new Deck();
        playerHands = new Hand [players.size()];
        numBooksForPlayer = new int[players.size()];
        currentPlayer = 0;
    }

    /**
     * The turn for the player with all the actions they take. A pseudo main function for Game that runs all the pieces
     * @return the events that happened in a String format so the main can print
     */
    public String oneTurn (){
        String events = "";

        //draws a card if hand is empty and checks for a book
        int lastDrawnRank;
        if(ifEmptyDraw()) {
            lastDrawnRank = playerHands[currentPlayer].getCard(playerHands[currentPlayer].size() - 1).getRank();
            events += checkBook(lastDrawnRank);
        }

        //passes turn in the case of an empty hand
        boolean guessCorrect = true;
        if (playerHands[currentPlayer].size() == 0){
            currentPlayer = (currentPlayer+1) % (players.size());
            return "Player " + currentPlayer + " can't do anything since they are out of the game or just made a book." + "\n";
        }

        Play playForThisTurn = players.get(currentPlayer).doTurn(playerHands[currentPlayer]);
        int targetPlayer = playForThisTurn.getTargetPlayer();
        int targetCard = playForThisTurn.getRank();

        while (guessCorrect) {

            if (playerHands[targetPlayer].hasRank(targetCard)) {
                List<Card> returnedCards = removeRankFromHand(targetCard, targetPlayer);

                for (int i = 0; i < returnedCards.size(); i++) {
                    playerHands[currentPlayer].cards.add(returnedCards.get(i));
                }

                RecordedPlay recordedTurn = new RecordedPlay(currentPlayer, targetPlayer, targetCard, returnedCards);
                updatePlayerGameData(recordedTurn);
                events += "Player " + currentPlayer + " asks Player " + targetPlayer + " for " +
                        Card.SHORT_NAMES[targetCard] + "s and got " + returnedCards.size() + " card(s)" + "\n";
                updatePlayerGameData(recordedTurn);
                events += checkBook(targetCard);

                if (playerHands[currentPlayer].size() != 0) {
                    playForThisTurn = players.get(currentPlayer).doTurn(playerHands[currentPlayer]);
                    targetPlayer = playForThisTurn.getTargetPlayer();
                    targetCard = playForThisTurn.getRank();
                }
            } else {
                RecordedPlay recordedTurn = new RecordedPlay(currentPlayer, targetPlayer, targetCard, new ArrayList<Card>());
                updatePlayerGameData(recordedTurn);
                events += "Player " + currentPlayer + " asks Player " + targetPlayer + " for " +
                        Card.SHORT_NAMES[targetCard] + "s and got 0 card(s)" + "\n";

                if (!gameDeck.isEmpty()) {
                    playerHands[currentPlayer].cards.add(gameDeck.draw());
                    lastDrawnRank = playerHands[currentPlayer].getCard(playerHands[currentPlayer].size() - 1).getRank();
                    events += checkBook(lastDrawnRank);
                }
                guessCorrect = false;
            }
        }
        currentPlayer = (currentPlayer+1) % (players.size());
        return events;
    }

    /**
     * Simplified version of the initial deal, instead of dealing one card to each person clockwise until each person
     * has 5 cards, we just deal 5 cards to each person. This is the same thing due to the shuffle function in the deck,
     * the whole reason why cards are dealt one at a time is due to unsatisfactory shuffling, whereas the Collection
     * shuffle function makes such a reason obsolete.
     */
    public void dealInitialCards(){
        for(int i = 0; i < players.size(); i++){
            playerHands[i] = new Hand(gameDeck.draw(5));
        }
    }

    /**
     * Updates all player's internal data with the plays currently happening in the game
     * @param recordedTurn The recorded turn that was created with the info of what happened
     */
    private void updatePlayerGameData(RecordedPlay recordedTurn){
        for (PlayerStrategy player : players){
            player.playOccurred(recordedTurn);
        }
    }

    /**
     * If the player's hand is empty then we draw, we do not draw if the deck is empty
     * @return true if we drew, false if we didn't
     */
    public boolean ifEmptyDraw(){
        if (gameDeck.isEmpty())
            return false;

        if(playerHands[currentPlayer].size() == 0){
            playerHands[currentPlayer].cards.add(gameDeck.draw());
        }
        return true;
    }

    /**
     * Remove function that subtracts cards by rank from a specified person's hand and returns all cards subtracted
     * @param targetRank target card rank to be removed
     * @param targetPlayer the specified player that cards are removed from
     * @return all cards removed from the target player
     */
    public List<Card> removeRankFromHand(int targetRank, int targetPlayer){
        List <Card> removedElems = new ArrayList<>();

        for (Card targetCard : new ArrayList<>(playerHands[targetPlayer].cards)) {
            if (targetCard.getRank() == targetRank) {
                removedElems.add(new Card(targetCard.getRank(), targetCard.getSuit()));
                playerHands[targetPlayer].cards.remove(targetCard);
            }
        }
        return removedElems;
    }

    /**
     * Checks if a player has a book in their hand by seeing if they have four of a kind
     * @param rank the rank of the card we are checking for
     * @return a string of the book output text
     */
    public String checkBook(int rank){
        String event = "";
        int numContained = 0;
        for (int i = 0; i < playerHands[currentPlayer].size(); i++){
            if (playerHands[currentPlayer].getCard(i).getRank() == rank)
                numContained++;
        }
        if(numContained == CARDS_FOR_A_BOOK){
            event += "Player " + currentPlayer + " made a book of " + (Card.SHORT_NAMES[rank]) + "s." + "\n";
            removeRankFromHand(rank, currentPlayer);
            numBooksForPlayer[currentPlayer] += 1;
        }
        return event;
    }

    /**
     * Detects when the game is over by checking if all 13 books have been completed yet
     * @return a boolean true for if it is over and false if it is not
     */
    public boolean gameOver(){
        int numberOfBooks = 0;
        for (int playerBooks : numBooksForPlayer)
            numberOfBooks += playerBooks;
        if (numberOfBooks == Card.NUM_RANKS)
            return true;
        return false;
    }

    /**
     * Finds the winner by detecting who has the most books(points)
     * @return returns the winner of the game by player number
     */
    public int getWinner(){
        int indexOfLargest = 0;
        int largest = 0;
        for(int i = 0; i < numBooksForPlayer.length; i++){
            if(numBooksForPlayer[i] > largest){
                largest = numBooksForPlayer[i];
                indexOfLargest = i;
            }
        }
        return indexOfLargest;
    }
}
