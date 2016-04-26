package org.pankuzu.test.di.jonasboner.step2

/**
  * Jonas Boner （Akka の作者）が2008年に書いた
  * "Real-World Scala: Dependency Injection (DI)"
  * （http://jonasboner.com/real-world-scala-dependency-injection-di/）
  *
  * の日本語訳より
  * http://eed3si9n.com/ja/real-world-scala-dependency-injection-di
  *
  * ver. step 2
  * ・問題点
  *   サービスの実装と作成が密結合
  *   配線構成（wiring configuration）がコードのあちこちに散在している
  *
  */
object Boot extends App {
  val userService = ComponentRegistry.userService
  val user = userService.authenticate("olive", "pass")

}

/**
  * レジストリ・オブジェクト
  * 名前空間を統合するオブジェクト
  */
object ComponentRegistry extends UserServiceComponent with UserRepositoryComponent

/**
  * UserRepository を UserRepositoryComponent trait でラッピングし
  * UserRepository のインスタンスを作成する
  * => UserRepository の名前空間（UserRepositoryComponent）を作成した事になる
  */
trait UserRepositoryComponent {
  val userRepository = new UserRepository
  class UserRepository {
    def authenticate(user: User): User = {
      println(s"authenticatig user: ${user.name}(${user.password})")
      user
    }

    def create(user: User) = println(s"creating user: ${user.name}(${user.password})")
    def delete(user: User) = println(s"deleting user: ${user.name}(${user.password})")
  }
}

/**
  * UserService はリポジトリインスタンスをインジェクトして欲しい
  * => UserService を UserServiceComponent trait でラッピングし
  *    自分型アノテーション（self-type annotation）で UserRepository への依存を宣言する
  */
trait UserServiceComponent { this: UserRepositoryComponent =>
  val userService = new UserService
  class UserService {
    def authenticate(name: String, password: String): User ={
      userRepository.authenticate(new User(name, password))
    }

    def create(name: String, password: String) = {
      userRepository.create(new User(name, password))
    }
    def delete(user: User) = userRepository.delete(user)
  }

}

class User(val name: String, val password: String)