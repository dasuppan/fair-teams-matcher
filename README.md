# Fair Teams Matcher

A command line application for finding fair random teams based on players experience. It uses a simple recursive approximation algorithm. This application was initially made for Minecraft Hungergames but can be used for any team-based game/sport/etc.

# Usage

1. Compile the Java-File `Matcher.java` in the `src` folder with the following command
    ```
    javac Matcher.java
    ```

2. Create the file containing players following the pattern `PLAYERNAME XP`. Players are separated using newline. XP should hereby represent the players experience in relation to the other players. It can be seen as a "star-rating" meaning a value of 1 would be fitting for a bloody beginner and 5 for a highly experienced player.
See the file `src/example.txt` as an example.

3. Decide on the preferred size of the teams. Naturally, teams with equal amount of members are likely to be more balanced. 

4. Decide if you either want
    - more randomness and less balancing (*non-strict*) or
    - less randomness and more balanced teams (*strict*).

    Depending on your choice write `strict` at the end of the command to enable strict-mode.

5. Run the matcher with the following command
    ```
    java Matcher path-to-playerfile teamsize [strict]
    ```

6. Voila, you got your teams. If you are not happy with the teams either run the matcher again or play with the *strict* flag if you want to tweak the outcome. Have fun!

# Contributing

If you have ideas on how to improve this application feel free to open an Issue or create a pull request!
