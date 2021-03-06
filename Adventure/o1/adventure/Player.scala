package o1.adventure

import scala.collection.mutable.Map


class Player(startingArea: Area, adventure: Adventure) {

  private val r = scala.util.Random                                 // Random generation
  private val jokeMoney = 150                                       // amount of money that Borat earns by telling a joke
  private var currentLocation: Area = startingArea                  // gatherer: changes in relation to the previous location
  private var quitCommandGiven: Boolean = false                     // one-way flag
  private var playerItems = Map[String, Option[Item]]()             // player's items are stores in this variable
  var count = 0                                                     // counts how many times unraveled

  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven

  /** Returns the current location of the player. */
  def location = this.currentLocation

  /** Borat flies to destination. This is successful if
    * there is enough money to buy a flight ticket
    * desination is not the current location
    * destination exists on the map */
  def fly(state: String): String = {
    val destination: Option[Area] = this.adventure.checkAreas.get(state)
    if(destination.isDefined && this.currentLocation == destination.get) return "Sorry, you drunk? Borat already in " + this.currentLocation.name + "."
    if(destination.isDefined && this.adventure.money >= 250) {
      this.currentLocation = destination.get
      this.adventure.money -= 250
      "Borat flies to " + destination.get.name.capitalize + " for 250 dollars."
    } else if(state == "") "Borat is confused. You can't fly to emptiness!\nPlease, remember to write \"fly\" command with state's name."
    else if(destination.isEmpty) "Sorry, it's not possible to fly to " + state.capitalize +". " + "Try another destination."
     else "Sorry, Borat doesn't have enough money to fly to " + state.capitalize + ". A ticket costs 250$."
  }

  /** Borat entertains.
    * entertain is randomly generated --> Player can't affect which joke is told. */
  def entertain(): String = {
    this.adventure.money += jokeMoney
    var randomNum = r.nextInt(8)
    val text = "Borat diverts in an cheerful manner... "
    val textMoney = "\n" + "Borat made " + this.jokeMoney + " dollars by entertaining."
    randomNum match {
      case 0 => text + "\n" + "Althoug Kazakhstan a glorious country, it have a problem too: economic, social and Jew." + "\n" + textMoney
      case 1 => text + "\n" + "I want to have a car that attract a woman with shave down below." + "\n" + textMoney
      case 2 => text + "\n" + "My wife, she make this cheese. She make it with milk from her teat." + "\n" + textMoney
      case 3 => text + "\n" + "Oh well, King in the Castle, King in the Castle, I have a chair! Go do dis, go do that, King in the Castle." + "\n" + textMoney
      case 4 => text + "\n" + "Wawaweewaa! High-five!" + "\n" + textMoney
      case 5 => text + "\n" + "The only thing keeping me going was my dream of one day holding Pamela in my arms and making romance explosion on her stomach." + "\n" + textMoney
      case 6 => text + "\n" + "When you chase a dream, especially one with plastic chests, you sometimes do not see what is right in front of you."+ "\n" + textMoney
      case 7 => text + "\n" + "Sometime my sister, she show her vashïn to my brother Bilo and say \"You will never get this you will never get this la la la la la.\"\nHe behind his cage. He cries and everybody laughs. She goes \"You never get this.\"\nBut one time he break cage and he 'get this' and then we all laugh. High five!" + "\n" + textMoney
    }
  }

  /** Attempts to move the player in the given state. This is successful if there
    * is an exit from the player's current location towards neighboring state. */
  def drive(state: String): String = {
    val destination: Option[Area] = this.adventure.checkAreas.get(state)
    if(destination.isDefined && this.currentLocation == destination.get) return "Borat no understand! Borat is already in " + this.currentLocation + "."
    if(state == "") "Borat is confused. You can't drive to emptiness!\nPlease, remember to write \"drive\" command with state's name."
    else if(destination.isDefined && currentLocation.checkNeighbors.contains(destination.get)) {
      this.currentLocation = destination.getOrElse(this.currentLocation)
      "Borat drives to " + destination.get.name.capitalize + "."
    } else if(destination.isEmpty) "Sorry, it's not possible to drive to " + state.capitalize +". " + s"${state.capitalize} doesn't exist on the map."
    else "Sorry, it's not possible to drive to " + state.capitalize +". " + s"${state.capitalize} is not the neighbour of ${this.currentLocation.name.capitalize}."
  }

  /** Checks the unraveled name of the state where Pamela resides */
  def check: String = {
    if(count == 0){
      "Borat hasn't unraveled a character yet, so there's no point in using this command yet."
    } else this.adventure.pamela.openWord
  }

  /** Unravels one character in the name of the state where Pamela resides
    * This method can be used only once! */
  def unravel(): String = {
    if(this.adventure.azamat.has("budweiser") && this.count == 0) {
      this.adventure.pamela.unravelWord()
      this.count = 1
      "Borat has successfully unraveled one character of state's name where Pamela resides." + "\n" + "Command \"check\" to check the unraveled name."
    } else if(count != 0) "Borat has already unraveled a character! Only one character can be unraveled."
    else "A character of Pamela's state's name can't be unraveled. Azamat will help you with that!"
  }

  /** Calls Azamat to find out his location
    * if u visit Azamat and give him a Budweiser (get it from Missouri), he will tell you one character of state's name where Pamela resides */
  def call: String = {
    println("Borat calls Azamat...")
    println("Azamat! Cен қайдасың? (Azamat! In which state are you?)")
    "Мен кіремін " + this.adventure.azamat.state.name.capitalize + "!" + " (I am now in " + this.adventure.azamat.state.name.capitalize + "!)"
  }

  /** Buy an item */
  def buy(itemName: String): String = {
    var nowLocation: Area = location
    if(nowLocation.contains(itemName) && this.adventure.money >= 150 && itemName != "mask" && itemName != "coat") {
      this.playerItems += itemName -> nowLocation.removeItem(itemName)
      this.adventure.money -= 150
      "Borat buys a " + itemName + " for 150 dollars."
    } else if(this.adventure.money < 150) s"Sorry, Borat doesn't have enough money to buy a ${itemName}. A ${itemName} costs 150 dollars." + "\n" + "Maybe Borat should try to tell a joke to make some money?"
    else if(itemName == "mask" || itemName == "coat") s"No need to buy a ${itemName}. " +  "Just command \"use\" " + s"+ ${itemName} in order to wear it!"
    else if(itemName == "") "Borat is confused. You can't buy emptiness!\nPlease, remember to write \"buy\" command with item's name."
    else "There is no " + itemName + " here to buy."
  }

  /** Gives an item to someone */
  def give(itemName: String): String = {
    if(playerItems.contains(itemName)) {
      var nowLocation = location
      nowLocation.checkCharacters.values.toVector(0).receive(itemName, playerItems(itemName))
      playerItems.remove(itemName)
      "Borat gives the " + itemName + {if(this.location.checkCharacters.nonEmpty) " to " + this.location.checkCharacters.keys.toVector(0).capitalize + ". Azamat like!"} + "\n" + s"${this.adventure.azamat.name.capitalize} gave Borat a tip that will ease finding Pamela." + "\n" + "Command \"unravel\" to unravel a character."
    } else if(itemName == "") "Borat is confused. You can't give emptiness!\nPlease, remember to write \"give\" command with item's name."
    else "Borat doesn't have that!"
  }

  /** Checks how much money and what items does Borat have */
  def inventory: String = {
    if(playerItems.nonEmpty) s"Borat has ${this.adventure.money} dollars left and " + "is carrying:\n" + playerItems.keys.mkString("\n")
    else if (this.adventure.money > 0) s"Borat has ${this.adventure.money} dollars left and is empty-handed otherwise."
    else "Borat is totally empty-handed."
  }

  /** Kisses a target character
    * Once Pamela is kissed, the game is won! */
  def kiss: String = {
    if(this.currentLocation == this.adventure.pamela.state) {
      this.adventure.pamela.kissed = true
      "Borat kissed Pamela!"
    } else "Borat needs to be close to Pamela in order to kiss her!"
  }

  def use(itemName: String): String = {
    var nowLocation = location
    if(nowLocation.contains(itemName) && itemName != "budweiser") {
      this.playerItems += itemName -> nowLocation.removeItem(itemName)
      this.adventure.turnCount -= 3
      "Borat picks up the " + itemName + " and puts it on. Police won't recognize Borat for a while now!"
    } else if(playerItems.contains(itemName) && itemName == "budweiser") {
      this.playerItems.remove(itemName)
      "Borat drinks the Budweiser! Borat feels boozy."
    } else if(itemName == "") "Borat is confused. You can't use emptiness!\nPlease, remember to write \"use\" command with item's name."
    else "There is no " + itemName + " here to use."
  }

  /** Signals that the player wants to quit the game. Returns a description of what happened within
    * the game as a result (which is the empty string, in this case). */
  def quit() = {
    this.quitCommandGiven = true
    ""
  }

  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name

  /** Open instuctions on how to play the game */
  def help: String = {
      this.adventure.turnCount -= 1
      s"\nAll commands of the game:" +
      s"\ndrive + destination     = drive to target destination. This command is successful if target desination does neighbour Borat's current location." +
      s"\nfly + destination       = fly to target destination. This command is successful if destination exists and there is enough money to buy a flight ticket." +
      s"\ninventory               = check how much money and what items does Borat have at the moment." +
      s"\nentertain               = entertain people around. Entertaining will earn money." +
      s"\nunravel                 = unravel one character in the name of the state where Pamela resides. This command will work only after giving a Budweiser to Azamat!" +
      s"\ncheck                   = print the unraveled name of the state where Pamela resides." +
      s"\ncall                    = call Azamat to find out where he resides." +
      s"\ngive + item's name      = give a Budweiser to Azamat. Happy Azamat will tell you a hint where Pamela resides." +
      s"\nbuy + item's name       = buy a Budweiser." +
      s"\nuse + item's name       = use the target item. Using mask, hat or coat will campouflage u for the police for three turns. Using the Budwiser will drink it." +
      s"\nkiss                    = kiss Pamela in order to win the game. You need to be in the same location with Pamela in order to be able to kiss!" +
      s"\nquit                    = quit the game." +
      s"\nhelp                    = print out the instructions." +
      s"\n\n-->The game is won by finding Pamela and kissing her<--" +
      s"\n\n\n"
  }


}


