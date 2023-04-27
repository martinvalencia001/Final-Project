import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

class Hand implements Iterable<Card> {
    public List<Card> cards;

    public Hand() {
        cards = new ArrayList<Card>();
    }

    public void newCard(Deck deck) {
        Card card = deck.drawCard();
        cards.add(card);
    }

    public int getHandValue() {
        int value = 0;
        int aces = 0;
        for (Card card : cards) {
            int cardValue = card.getValue();
            if (cardValue == 1) {
                aces++;
            }
            value += cardValue;
        }
        while (value < 11 && aces > 0) {
            value += 10;
            aces--;
        }
        return value;
    }

    public Iterator<Card> iterator() {
        return cards.iterator();
    }
}

class Card {
    private String suit;
    private String rank;
    private int value;

    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public int getValue() {
        return value;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            Card card = (Card) obj;
            return suit.equals(card.getSuit()) && rank.equals(card.getRank()) && value == card.getValue();
        }
        return false;
    }
}

class Deck {
    private List<Card> cards;
    private int currentCard;

    public Deck() {
        cards = new ArrayList<>();
        currentCard = 0;
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8","9", "10", "Jack", 
        "Queen", "King"};
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < ranks.length; j++) {
                Card card = new Card(suits[i], ranks[j], values[j]);
                cards.add(card);
            }
        }
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (currentCard == cards.size()) {
            currentCard = 0;
            Collections.shuffle(cards);
        }
        return cards.get(currentCard++);
    }
}

class Blackjack {
    private Hand dealer; // to hold the dealer's cards
    private Hand player; // to hold the player's cards
    private Deck deck; // a set of cards

    public Blackjack(Hand dealer, Hand player) {
        this.dealer = dealer;
        this.player = player;
        this.deck = new Deck();
    }

    /**
     * Deals the initial cards to each player.
     */
    public void dealInitialCards() {
        dealer.newCard(deck);
        dealer.newCard(deck);
        player.newCard(deck);
        player.newCard(deck);
    }

    /**
     * Adds the next random card from the deck to the given player's hand.
     */
    public void hit(Hand hand) {
        hand.newCard(deck);
    }

    public String calculateWinner() {
        int dealerValue = dealer.getHandValue();
        int playerValue = player.getHandValue();

        if (dealerValue > 21 && playerValue > 21) {
            return "Push";
        } else if (dealerValue > 21) {
            return "Player";
        } else if (playerValue > 21) {
            return "Dealer";
        } else if (dealerValue > playerValue) {
            return "Dealer";
        } else if (playerValue > dealerValue) {
            return "Player";
        } else {
            return "Push";
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Hand dealer = new Hand();
        Hand player = new Hand();
        Blackjack game = new Blackjack(dealer, player);

        game.dealInitialCards();
        System.out.println("Dealer's hand total is: " + 
       dealer.getHandValue());
        for (Card card : dealer) {
            System.out.println(card.getRank() + " of " + card.getSuit());
        }

        System.out.println("Player's hand total is: " + 
        player.getHandValue());
        for (Card card : player) {
            System.out.println(card.getRank() + " of " + card.getSuit());
        }

        String winner = game.calculateWinner();
        System.out.println("Winner: " + winner);
      
      if (winner=="Player"){
        System.out.println("Congratulations, you are the winner!");
      }
      if (winner=="Dealer"){
        System.out.println("Dealer won, better luck next time.");
      }
    }
}