package o1.adventure.ui


import o1.adventure._
import scala.io.StdIn._

/** The singleton object `AdventureTextUI` represents a fully text-based version of the
  * Adventure game application. The object serves as a possible entry point for the game,
  * and can be run to start up a user interface that operates in the text console. */

object AdventureTextUI extends App {

  private val game = new Adventure
  private val player = game.player
  this.run()


  /** Runs the game.
    * first, difficulty level is chosen by the player
    * second, a welcome message is printed, then the player gets the chance to
    * play any number of turns until the game is over, and finally a goodbye message is printed. */
  private def run() = {
    println()
    var difficultyLevel = ""
    do {
      difficultyLevel = readLine("Please, write down the difficulty level of the game (easy, medium, hard): ")
    } while(difficultyLevel.toLowerCase != "easy" && difficultyLevel.toLowerCase != "medium" && difficultyLevel.toLowerCase != "hard")
    if(difficultyLevel == "easy") game.timeUntilPolice = 16 else if(difficultyLevel == "medium") game.timeUntilPolice = 12 else game.timeUntilPolice = 9
    println(this.game.welcomeMessage)
    while (!this.game.isOver) {
      this.printAreaInfo()
      this.playTurn()
    }
    println(this.game.goodbyeMessage)
  }


  /** Prints out a description of the player character's current location, as seen by the character. */
  private def printAreaInfo(): Unit = {
    val area = this.player.location
    println("\n" + "Borat is now in " + area.name + "!")
    println(area.fullDescription)
    println("-" * area.name.length + "-" * 30)
  }


  /** Requests a command from the player, plays a game turn accordingly, and prints out a report of what happened.  */
  private def playTurn(): Unit = {
    println()
    val command = readLine("Command: ")
    println()
    val turnReport = this.game.playTurn(command)
    if(!turnReport.isEmpty) {
      println(turnReport)
    }
  }


}
