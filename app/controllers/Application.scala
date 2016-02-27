package controllers

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._

case class User(id: Int, firstName: String, lastName: String)

class Application extends Controller {

  implicit val reads: Reads[User] = (
    (__ \ "id").read[Int] and
      (__ \ "name" \ "first").read[String] and
      (__ \ "name" \ "last").read[String]
    )(User.apply _) //((id, firstName, lastName) => User(id, firstName, lastName))

  implicit val writers: Writes[User] = (
    (__ \ "id").write[Int] and
      (__ \ "name" \ "first").write[String] and
      (__ \ "name" \ "last").write[String]
    )(unlift(User.unapply)) //(user => (user.id, user.firstName, user.lastName))

  def index = Action {
    val users = Seq(
      User(id = 1, firstName = "Ted", lastName = "Right"),
      User(id = 2, firstName = "Frank", lastName = "Honest"),
      User(id = 3, firstName = "Joan", lastName = "Well"),
      User(id = 4, firstName = "Gail", lastName = "Polite"),
      User(id = 5, firstName = "Michael", lastName = "Fair")
    )
    Ok(views.html.index(Json.toJson(users).toString()))
  }

  def save = Action(parse.json) { implicit request =>
    request.body.validate[Seq[User]].map {
      case users =>
        users.foreach(println)
        Ok(Json.obj("status" -> "OK"))
    }.recoverTotal { e =>
      println(JsError.toFlatForm(e))
      BadRequest(Json.obj("status" -> "NG"))
    }
  }

}
