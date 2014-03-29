package controllers

import play.api.mvc._
import play.api.libs.json._

/**
 * Created by oriolblanc on 29/03/14.
 */

case class Price(amount: Int, currency: String)
case class MenuItem(name: String, price: Price)
case class Menu(items: Seq[MenuItem])

object MenuProtocol {
  implicit val priceFormat = Json.writes[Price]
  implicit val menuItemFormat = Json.writes[MenuItem]
  implicit val menuFormat = Json.writes[Menu]
}

object MenuController extends Controller{

  def retrieveMenu = Action {
    import MenuProtocol._
    val menu : Menu = Menu(
      Seq(
        MenuItem("Arroz chino 3 delicias", Price(4, "EUR")),
        MenuItem("Pato pekin", Price(4, "EUR")),
        MenuItem("Li chis", Price(2, "EUR"))))
    Ok(Json.toJson(menu))
  }
}
