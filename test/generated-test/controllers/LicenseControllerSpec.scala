package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.i18n._

import play.api.data._
import play.api.data.Forms._

import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._

import play.filters.csrf.CSRF.Token
import play.filters.csrf.{CSRFConfigProvider, CSRFFilter}

/**
 * License form controller specs
 */
class LicenseControllerSpec extends PlaySpec with OneAppPerTest {

  def addToken[T](fakeRequest: FakeRequest[T]) = {
    val csrfConfig     = app.injector.instanceOf[CSRFConfigProvider].get
    val csrfFilter     = app.injector.instanceOf[CSRFFilter]
    val token          = csrfFilter.tokenProvider.generateToken

    fakeRequest.copyFakeRequest(tags = fakeRequest.tags ++ Map(
    Token.NameRequestTag  -> csrfConfig.tokenName,
    Token.RequestTag      -> token
    )).withHeaders((csrfConfig.headerName, token))
  }

  "LicenseController GET" should {

    "render the index page from a new instance of controller" in {
      implicit val messagesApi = app.injector.instanceOf[MessagesApi]
      val controller = new LicenseController
      val request = addToken(FakeRequest())
      val home = controller.licenseGet().apply(request)

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
    }

    "render the index page from the application" in {
      val controller = app.injector.instanceOf[LicenseController]
      val request = addToken(FakeRequest())
      val home = controller.licenseGet().apply(request)

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
    }

    "render the index page from the router" in {
      val request = addToken(FakeRequest(GET, "/license")
        .withHeaders("Host" -> "localhost"))
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
    }
  }

  "LicenseController POST" should {
    "process form" in {
      val request = {
        FakeRequest(POST, "/license")
          .withHeaders("Host" -> "localhost")
          .withFormUrlEncodedBody("name" -> "play", "age" -> "4")
      }
      val home = route(app, request).get

      status(home) mustBe SEE_OTHER
    }
  }

}
