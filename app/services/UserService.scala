package services

import anorm._
import anorm.SqlParser._
import play.api.db.DBApi
import controllers.UserData
import models.UserView
import javax.inject._
import org.mindrot.jbcrypt.BCrypt

@Singleton
class UserService @Inject()(implicit dbapi: DBApi) {

    private val db = dbapi.database("default")

    private def createAdmin(): Unit = {
        if(!userExists("admin")) {
            createUser(UserData("Administrator", "admin", "admin@", "admin"))
        }
    }

    def createUser(user: UserData) {
        db.withConnection { implicit c =>
        //If you are inserting data that has an auto-generated Long primary key, you can call executeInsert().
        val id: Option[Long] = SQL(
            """
            insert into Users(name, username, email, passwordHash) 
            values ({name}, {username}, {email}, {passwordHash})
            """
            ).on('name -> user.name, 'username -> user.username, 
            'email -> user.email, 'passwordHash -> hashPassword(user.password)).executeInsert()
        }
    }

    private def hashPassword(password: String): String = {
        BCrypt.hashpw(password, BCrypt.gensalt());
    }

    val userPasswordHashSQL = SQL(
            """
            select passwordHash from Users where username = {username}
            """
        )

    private def getPasswordHash(username: String): String = {
        val passwordHash = db.withConnection { implicit c =>
            userPasswordHashSQL.on('username -> username).as(scalar[String] .single)
        }
        passwordHash
    }

    def authenticate(username: String, password: String): Option[String] = {
        //check if user exists in the system
        if(userExists(username)) {
            //get current hashed password
            val hashed = getPasswordHash(username)
            val matched = BCrypt.checkpw(password, hashed)
            if (matched) {
                Some(username)
            } else {
                None
            }
        } else {
            //redirect to error page
            None
        }
    }

    val userExistsSQL = SQL(
        """
        select count(*) from Users where username = {username}
        """
        )

    private def userExists(username: String): Boolean = {
        db.withConnection { implicit c =>
            val count = userExistsSQL.on('username -> username).as(scalar[Long] .single)
            count == 1
        }
    }

    val selectAllUsersSQL = SQL(
        """
        select * from Users
        """
    )

    val allUserParser = str("name") ~ str("username") ~ str("email") map { to (UserView.apply _) }

    def getUsers(): List[(UserView)] = {
         db.withConnection { implicit c => 
            selectAllUsersSQL.as(allUserParser.*)
         }
    } 

    createAdmin

}