package o1.adventure



class Character(val name: String, val description: String, val state: Area) {

  private val r = scala.util.Random                             // random generation
  var kissed: Boolean = false                                   // indicates if a character has been kissed. (When Pamela is kissed. The game is won)
  private var characterItems = Map[String, Option[Item]]()      // stores items for the character
  var openWord: String = ""                                     // the unraveled state name is stored in this variable
  this.state.addCharacter(this)                                 // adds character to Area upon creation of the character

  /** Returns a short textual representation of the item (its name, that is). */
  override def toString = this.name

  /** This method stores item in characterItems once the item has been given by the Player */
  def receive(itemName: String, item: Option[Item]) = this.characterItems = this.characterItems + (itemName -> item)

  /** Opens one character in the name of the state where Pamela resides
    * this method is used by Class Player's method Unravel see @param unravel */
  def unravelWord(): Unit = {
    val stateName: String = state.name
    val randNum = r.nextInt(stateName.length)
    for(i <- stateName.indices) {
      if(i == randNum) this.openWord += stateName(i) else this.openWord += "_"
    }
  }

  /** Checks if character has some item */
  def has(itemName: String): Boolean = if(characterItems.exists(_._1 == itemName)) true else false


}


