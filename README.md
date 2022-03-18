# Flip_Flop

## Zookeeper for session management

While it is common to use session management tools provided within web framework, this project will use zookeeper to achieve that for the following reason:
1. Want to facilitate event such as user leave etc
2. The house owner of the game likely to change from time to time while zookeeper's tree structure is good for that
3. Easily manage session key through zookeeper Znode Value
4. I want to practice zookeeper