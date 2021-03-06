package o1.adventure



class Action(input: String) {

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim

  /** Causes the given player to take the action represented by this object, assuming
    * that the command was understood. Returns a description of what happened as a result
    * of the action (such as "You go west."). The description is returned in an `Option`
    * wrapper; if the command was not recognized, `None` is returned. */
  def execute(actor: Player): Option[String] = this.verb match {
    case "inventory"  => Some(actor.inventory)
    case "kiss" => Some(actor.kiss)
    case "fly"  => Some(actor.fly(this.modifiers))
    case "entertain" => Some(actor.entertain())
    case "drive" => Some(actor.drive(this.modifiers))
    case "check" => Some(actor.check)
    case "unravel" => Some(actor.unravel())
    case "call" => Some(actor.call)
    case "give"  => Some(actor.give(this.modifiers))
    case "quit"  => Some(actor.quit())
    case "buy"  => Some(actor.buy(this.modifiers))
    case "use"  => Some(actor.use(this.modifiers))
    case "help" => Some(actor.help)
    case other   => None
  }

  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = this.verb + " (modifiers: " + this.modifiers + ")"


}

