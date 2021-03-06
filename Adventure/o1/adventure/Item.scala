package o1.adventure


class Item(val name: String, val description: String, state: Area) {

  this.state.addItem(this)          // adds item to Area upon creation of the item

  /** Returns a short textual representation of the item (its name, that is). */
  override def toString = this.name

}





