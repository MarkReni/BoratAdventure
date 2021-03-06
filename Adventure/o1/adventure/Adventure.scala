package o1.adventure


class Adventure {

  private val r = scala.util.Random                                  // random generation
  val title = "Help Borat to find his Pamela Anderson"               // the title of the adventure game
  var money = 500                                                    // the initial money amount Borat has
  var turnCount = 0                                                  // the number of turns that have passed since the start of the game
  var timeUntilPolice = 0                                            // the maximum number of turns that this adventure game allows before Borat gets caught by the police

  /** Define areas */
  private val washington = new Area("washington")
  private val oregon = new Area("oregon")
  private val california = new Area("california")
  private val idaho = new Area("idaho")
  private val nevada = new Area("nevada")
  private val utah = new Area("utah")
  private val arizona = new Area("arizona")
  private val montana = new Area("montana")
  private val wyoming = new Area("wyoming")
  private val colorado = new Area("colorado")
  private val newMexico = new Area("new mexico")
  private val northDakota = new Area("north dakota")
  private val southDakota = new Area("south dakota")
  private val nebraska = new Area("nebraska")
  private val kansas = new Area("kansas")
  private val oklahoma = new Area("oklahoma")
  private val texas = new Area("texas")
  private val minnesota = new Area("minnesota")
  private val iowa = new Area("iowa")
  private val missouri = new Area("missouri")
  private val arkansas = new Area("arkansas")
  private val louisiana = new Area("louisiana")
  private val allAreas = Map[String, Area](washington.name -> washington, oregon.name -> oregon, california.name -> california, idaho.name -> idaho,
                                nevada.name -> nevada, utah.name -> utah, arizona.name -> arizona, montana.name -> montana, wyoming.name -> wyoming,
                                colorado.name -> colorado, newMexico.name -> newMexico, northDakota.name -> northDakota, southDakota.name -> southDakota,
                                nebraska.name -> nebraska, kansas.name -> kansas, oklahoma.name -> oklahoma, texas.name -> texas, minnesota.name -> minnesota,
                                iowa.name -> iowa, missouri.name -> missouri, arkansas.name -> arkansas, louisiana.name -> louisiana)

  washington.setNeighbors(Vector(idaho, oregon))
      oregon.setNeighbors(Vector(washington, idaho, nevada, california))
  california.setNeighbors(Vector(oregon, nevada, arizona))
       idaho.setNeighbors(Vector(washington, montana, wyoming, utah, nevada, oregon))
      nevada.setNeighbors(Vector(oregon, idaho, utah, arizona, california))
        utah.setNeighbors(Vector(idaho, wyoming, colorado, newMexico, arizona, nevada))
     arizona.setNeighbors(Vector(california, nevada, utah, colorado, newMexico))
     montana.setNeighbors(Vector(northDakota, southDakota, wyoming, idaho))
     wyoming.setNeighbors(Vector(montana, southDakota, nebraska, colorado, utah, idaho))
    colorado.setNeighbors(Vector(wyoming, nebraska, kansas, oklahoma, newMexico, arizona, utah))
   newMexico.setNeighbors(Vector(colorado, oklahoma, texas, arizona, utah))
 northDakota.setNeighbors(Vector(montana, southDakota, minnesota))
 southDakota.setNeighbors(Vector(northDakota, minnesota, iowa, nebraska, wyoming, montana))
    nebraska.setNeighbors(Vector(southDakota, iowa, missouri, kansas, colorado, wyoming))
      kansas.setNeighbors(Vector(nebraska, missouri, oklahoma, colorado))
    oklahoma.setNeighbors(Vector(kansas, missouri, arkansas, texas, newMexico, colorado))
       texas.setNeighbors(Vector(oklahoma, arkansas, louisiana, newMexico))
   minnesota.setNeighbors(Vector(iowa, northDakota, southDakota))
        iowa.setNeighbors(Vector(minnesota, missouri, nebraska, southDakota))
    missouri.setNeighbors(Vector(iowa, arkansas, oklahoma, kansas, nebraska))
    arkansas.setNeighbors(Vector(missouri, louisiana, texas, oklahoma))
   louisiana.setNeighbors(Vector(arkansas, texas))

  /** The character that the player controls in the game. */
  val player = new Player(washington, this)

  /** The characters that the player doesn't control are created in the game */
  val pamela = new Character("pamela", "The target of Borat's belovement.", this.allAreas.values.toVector(randomizeNum))
  val azamat = new Character("azamat", "The irascible producer who accompanies Borat on his journey", this.allAreas.values.toVector(randomizeNum))

  /** The items are created for the game */
  val budWeiser = new Item("budweiser", "Azamat loves budweiser!", missouri)
  val invisibleCoat1 = new Item("coat", "Use mask to hide from the police", this.allAreas.values.toVector(randomizeNum))
  val invisibleCoat2 = new Item("coat", "Use mask to hide from the police", this.allAreas.values.toVector(randomizeNum))
  val invisibleCoat3 = new Item("coat", "Use mask to hide from the police", this.allAreas.values.toVector(randomizeNum))
  val invisibleCoat4 = new Item("coat", "Use mask to hide from the police", this.allAreas.values.toVector(randomizeNum))
  val invisibleMask1 = new Item("mask", "Use mask to hide from the police", this.allAreas.values.toVector(randomizeNum))
  val invisibleMask2 = new Item("mask", "Use mask to hide from the police", this.allAreas.values.toVector(randomizeNum))
  val invisibleMask3 = new Item("mask", "Use mask to hide from the police", this.allAreas.values.toVector(randomizeNum))
  val invisibleMask4 = new Item("mask", "Use mask to hide from the police", this.allAreas.values.toVector(randomizeNum))
  val invisibleHat1  = new Item("hat", "Use mask to hide from the police", this.allAreas.values.toVector(randomizeNum))
  val invisibleHat2  = new Item("hat", "Use mask to hide from the police", this.allAreas.values.toVector(randomizeNum))
  val invisibleHat3  = new Item("hat", "Use mask to hide from the police", this.allAreas.values.toVector(randomizeNum))
  val invisibleHat4  = new Item("hat", "Use mask to hide from the police", this.allAreas.values.toVector(randomizeNum))


  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete = this.pamela.kissed

  /** Other classes can check areas with below method */
  def checkAreas = this.allAreas

  /** Randomized number for character's location */
  private def randomizeNum: Int = {
    var randomNum = 0
    do {
      randomNum = r.nextInt(this.allAreas.size)
    } while(randomNum == 18)
    randomNum
  }

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeUntilPolice

  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = "\n" + "----------------------------------------" + "\n" + "Hello, my name is Borat! " + "I try to find my love Pamela Andersons and want to kiss her. " + "\n" + "Please, help me find her. Jagshemash!" +
                       "\n\n" + s"Borat has ${this.money} dollars in his pocket. Buying items and travelling by air costs money. Borat can make money by entertaining people around." +
                       "\n\n" + "Beware! The police is tracking borat turn by turn, so try not to waste any turns! " + "Use coat, hat or mask to camouflage Borat from police's sight for some number of turns." +
                       "\n\n" + "Command \"help\" to see the instructions of the game." + "\n" + "----------------------------------------"

  /** Returns a message that is to be displayed to the player at the end of the game. The message
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage = {
    if(this.isComplete)
      "\n" + "Borat found Pamela and kissed her. And they lived happily ever after... Borat like!\nAwesome job player. Chenquieh!"
    else if (this.turnCount == this.timeUntilPolice)
      "\n" + "Police has caught Borat! Game over..." + "\nPamela resided in... " + this.pamela.state.name.toUpperCase + ". "
    else  // game over due to player quitting
      "\n" + "You quit before Borat found his Pamela. Borat not happy! Arghh!" + "\nPamela resided in... " + this.pamela.state.name.toUpperCase + ". "
  }

  /** Plays a turn by executing the given in-game command, such as "go west". Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String) = {
    if((this.timeUntilPolice - this.turnCount) == 4) print("\n!!!Police is right behind Borat!!!\n\n\n")
    if((this.timeUntilPolice - this.turnCount) == 8) print("\n!!!Police is chasing Borat!!!\n\n\n")
    val action = new Action(command)
    val outcomeReport = action.execute(this.player)
    if(outcomeReport.isDefined) this.turnCount += 1
    outcomeReport.getOrElse("Unknown command: \"" + command + "\".")
  }


}







