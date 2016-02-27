package controllers

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._

case class User(id: Int, firstName: String, lastName: String)

class Application extends Controller {

  implicit val reads = (
    (__ \ 'id).read[Int] and
      (__ \ 'name \ 'first).read[String] and
      (__ \ 'name \ 'last).read[String]
    )((id, firstName, lastName) => User(id, firstName, lastName))

  def index = Action {
    Ok(views.html.index())
  }

  def save = Action(parse.json) { implicit request =>
    request.body.validate[Seq[User]].map {
      case users =>
        users.foreach(println)
        Ok(Json.obj("status" -> "OK"))
    }.recoverTotal { e =>
      BadRequest(Json.obj("status" -> "NG"))
    }
  }

}
