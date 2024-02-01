package ass1;

public class WarGame {
    public static void main(String[] args) {
        SQueue<Card> deck = new SQueue<>(52);

        for (Card.Suits suit : Card.Suits.values()) {
            for (Card.Ranks rank : Card.Ranks.values()) {
                Card card = new Card(suit, rank);
                deck.enqueue(card);
            }
        }

        deck.shuffle();

        SQueue<Card> player1Hand = new SQueue<>(52);
        SQueue<Card> player2Hand = new SQueue<>(52);

        boolean alternatePlayer = true;
        while (!deck.isEmpty()) {
            Card drawnCard = deck.dequeue();
            if (alternatePlayer) {
                player1Hand.enqueue(drawnCard);
            } else {
                player2Hand.enqueue(drawnCard);
            }
            alternatePlayer = !alternatePlayer;
        }

        int maxRounds = 50;
        int rounds = 0;
        int player1Wins = 0;
        int player2Wins = 0;

        System.out.println("Welcome to the Game of War!\n");
        System.out.println("Now dealing cards to the players\n");

        System.out.println("Player 1's Deck:");
        System.out.println(player1Hand.toString() + "\n");

        System.out.println("Player 2's Deck:");
        System.out.println(player2Hand.toString() + "\n");

        System.out.println("Starting the game of WAR!");
        System.out.println("Max number of rounds = " + maxRounds + "\n");

        while (rounds < maxRounds && !player1Hand.isEmpty() && !player2Hand.isEmpty()) {
            Card player1Card = player1Hand.dequeue();
            Card player2Card = player2Hand.dequeue();
            rounds++;

            int comparison = player1Card.compareTo(player2Card);

            if (comparison > 0) {
                player1Hand.enqueue(player1Card);
                player1Hand.enqueue(player2Card);
                System.out.println("Round " + rounds + ": Player 1 Wins!");
                player1Wins++;
            } else if (comparison < 0) {
                player2Hand.enqueue(player1Card);
                player2Hand.enqueue(player2Card);
                System.out.println("Round " + rounds + ": Player 2 Wins!");
                player2Wins++;
            } else {
                System.out.println("Round " + rounds + ": Time for War!");

                SQueue<Card> warPile1 = new SQueue<>(26);
                SQueue<Card> warPile2 = new SQueue<>(26);

                boolean warWinner = false;

                while (!warWinner) {
                    if (player1Hand.getSize() < 3 || player2Hand.getSize() < 3) {
                        while (!player1Hand.isEmpty()) {
                            warPile1.enqueue(player1Hand.dequeue());
                        }
                        while (!player2Hand.isEmpty()) {
                            warPile2.enqueue(player2Hand.dequeue());
                        }
                        int player1CardCount = warPile1.getSize() + player1Hand.getSize();
                        int player2CardCount = warPile2.getSize() + player2Hand.getSize();

                        if (player1CardCount > player2CardCount) {
                            warWinner = true;
                            System.out.println("WAR Winner: Player 1!");
                            player1Wins++;
                        } else if (player2CardCount > player1CardCount) {
                            warWinner = true;
                            System.out.println("WAR Winner: Player 2!");
                            player2Wins++;
                        } else {
                            System.out.println("WAR Tie! But no more cards to continue.");
                            warWinner = true;
                        }
                        break;
                    }

                    for (int i = 0; i < 3; i++) {
                        warPile1.enqueue(player1Hand.dequeue());
                        warPile2.enqueue(player2Hand.dequeue());
                    }

                    Card warCard1 = warPile1.dequeue();
                    Card warCard2 = warPile2.dequeue();
                    int warComparison = warCard1.compareTo(warCard2);

                    if (warComparison > 0) {
                        while (!warPile1.isEmpty()) {
                            player1Hand.enqueue(warPile1.dequeue());
                            player1Hand.enqueue(warPile2.dequeue());
                        }
                        warWinner = true;
                        System.out.println("WAR Winner: Player 1!");
                        player1Wins++;
                    } else if (warComparison < 0) {
                        while (!warPile1.isEmpty()) {
                            player2Hand.enqueue(warPile1.dequeue());
                            player2Hand.enqueue(warPile2.dequeue());
                        }
                        warWinner = true;
                        System.out.println("WAR Winner: Player 2!");
                        player2Wins++;
                    } else {
                        System.out.println("WAR Tie! Another round of WAR...");
                    }
                }
            }
        }

        System.out.println("\nAfter " + rounds + " rounds here are the results: ");
        System.out.println("Player 1: " + player1Hand.getSize() + " cards");
        System.out.println("Player 2: " + player2Hand.getSize() + " cards");

        if (player1Wins > player2Wins) {
            System.out.println("Player 1 wins the game!");
        } else if (player2Wins > player1Wins) {
            System.out.println("Player 2 wins the game!");
        } else {
            System.out.println("The game has ended in a tie.");
        }

        System.out.println("Player 1 had " + player1Wins + " wins");
        System.out.println("Player 2 had " + player2Wins + " wins");

    }
}


