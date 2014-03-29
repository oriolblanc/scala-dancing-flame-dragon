package controllers

import play.api.mvc._
import play.api.libs.json._

/**
 * Created by oriolblanc on 29/03/14.
 */

case class Price(amount: Int, currency: String)
case class MenuItem(name: String, price: Price)
case class Menu(items: Seq[MenuItem])
case class OrderItem(quantity:Int, menuItem:MenuItem)
case class MenuOrder(items: Seq[OrderItem])

object MenuProtocol {
  implicit val priceFormat = Json.format[Price]
  implicit val menuItemFormat = Json.format[MenuItem]
  implicit val menuFormat = Json.format[Menu]
  implicit val orderItemFormat = Json.format[OrderItem]
  implicit val menuOrderFormat = Json.format[MenuOrder]
}

object MenuController extends Controller{
  import MenuProtocol._

  val menu : Menu = Menu(Seq(MenuItem("Arroz chino 3 delícias", Price(4, "EUR")), MenuItem("Pato pekin", Price(4, "EUR")), MenuItem("Li chis", Price(2, "EUR"))))

  def indexMenu = Action { request =>
    val accept = request.headers.get(ACCEPT).getOrElse("text/html")

    accept match {
      case "application/json" => Ok(Json.toJson(menu))
      case _            => Ok(views.html.menu(menu))
    }
  }

  def placeOrder = Action(parse.json) { request =>
    val jResult = request.body.validate[MenuOrder]
    jResult match {
      case JsSuccess(order, _) => {
        println(order)
        Created
      }
      case JsError(errors) => BadRequest(errors.toString)
    }
  }
}
