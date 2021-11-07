
# Fair Teams Matcher

A small program for matching teams based on players skill. The problem of partitioning players into k teams while keeping the skill evenly distributed is a NP-complete problem and is also known as the [Partition problem](https://en.wikipedia.org/wiki/Partition_problem). This program implements the Karmarkar-Karp algorithm which utilizes the [Largest differencing method](https://en.wikipedia.org/wiki/Largest_differencing_method) and approximates good results. A nice read that helped me understand it was [this PDF](http://bit-player.org/wp-content/extras/bph-publications/AmSci-2002-03-Hayes-NPP.pdf). The teams are partly-randomized if some players have an equal skill score otherwise this algorithm does not do any randomization. This application was initially made for Minecraft Hungergames but can be used for any team-based game/sport/etc.

# Usage

0. Create a file which contains players, one player for one line, in the format of NAME,SKILLLEVEL as seen in the file `res/players.csv`. It does not necessarily need to be a `.csv` although I found it to be easy to work with.

1. Compile the Kotlin-File `Main.kt` and run it with two arguments, namely

- the file path of the created .csv-file and 
- the number of teams you want to generate.

2. Have fun!
3. 
