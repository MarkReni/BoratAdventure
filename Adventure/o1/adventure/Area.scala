package o1.adventure

import scala.collection.mutable.Map



class Area(var name: String) {

  private var neighbors = Vector[Area]()
  def checkNeighbors = this.neighbors
  private val items = Map[String, Item]()
  private val characters = Map[String, Character]()

  /** Returns a single-line description of the area for debugging purposes. */
  override def toString = this.name

  def checkCharacters = this.characters

  /** Adds exits from this area to the given areas. Calling this method is equivalent to calling
    * the `setNeighbor` method on each of the given direction--area pairs.
    * @param exits  contains pairs consisting of a direction and the neighboring area in that direction */
  def setNeighbors(exits: Vector[Area]) = this.neighbors ++= exits

  /** Returns a multi-line description of the area as a player sees it. This includes a basic
    * description of the area as well as information about exits and items. The return
    * value has the form "DESCRIPTION\n\nExits available: DIRECTIONS SEPARATED BY SPACES".
    * The directions are listed in an arbitrary order. */
  def fullDescription: String = {
    val exitList = "\n\nNeighbouring states: " + this.neighbors.map(_.name).map(_.capitalize).mkString(", ")

    if(this.items.nonEmpty && this.characters.nonEmpty) {
      "\n" + "You see here following items: " + "\n" + items.keys.map(_.capitalize).mkString("\n") +
      "\n" + "You also see here: " + "\n" + characters.keys.map(_.capitalize).mkString("\n") +
        exitList
    } else if(this.items.nonEmpty && this.characters.isEmpty) {
      "\n" + "You see here following items: " + "\n" + items.keys.map(_.capitalize).mkString("\n") + "\n" +
      exitList
    } else if(this.items.isEmpty && this.characters.nonEmpty) {
      "\n" + "You see here: " + "\n" + characters.keys.map(_.capitalize).mkString("\n") +
      exitList
    } else exitList
  }

  def addCharacter(character: Character): Unit = characters += character.name -> character

  def addItem(item: Item): Unit = items += item.name -> item

  def contains(itemName: String): Boolean = if(items.exists(_._1 == itemName)) true else false

  def removeItem(itemName: String): Option[Item] = items.remove(itemName)


}



