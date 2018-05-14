## Blackjack Project
### Week 3 Skill Distillery Homework

### Overview
- This project is a fully functioning simulation of Blackjack where the user plays against the dealer and gambles through each round.

### Topics
* Object-orientated design
* Interfaces
* Enums
* Inheritance
* Java Collections

### How to Run
- The user is prompted to enter his name and the deck is shuffled from a randomised multi deck shoe. Cards are dealt and the user is asked how much they wish to stake for the round.
- If the user/computer has Blackjack then the relevant payouts are made and the next round begins.
- Insurance can be taken up if the dealer is showing an Ace for his first card.
- If the user has two of the same card values then there is the option to split; doubling the stake by playing two separate hands.
- There is the option to double down if the user has two original cards. The stake will be doubled and the user will be given one more card.
- Normal gameplay consists of hitting and sticking
- The dealer will take its turn if the player has not already gone bust.
- The winner will be decided and chips adjusted accordingly.
- If the user chooses to play another round, a true count based on the High Lo card counting strategy will be displayed. This is adjusted for how many cards there are in the deck currently (randomised at start from 1 to 6).
- Once the user has no more money, the game finishes.

### Challenges and Lessons Learned
**1. Object Orientated Programming:**

- I had to refactor large parts of my code as I was passing in too many variables to my methods. The resulting version was a lot cleaner and consisted of less repeated code.

**2. Stretch goals:**

- Working out the order in which special conditions needed to be checked such as Blackjack or insurance was challenging. When I implemented them, they often had knock on consequences for the round's gameplay e.g. if the user chose insurance and the dealer got Blackjack then you could still continue playing the main hand.
- I ran into trouble implementing the soft/hard ace for the dealer as I had to make sure I covered every possibility and there were no null pointer exceptions. I ended up doing this separately from the normal totalling of card values.

**3. Enums:**
- First time using enums and this was particularly useful for having two different values for normal and card counting.
