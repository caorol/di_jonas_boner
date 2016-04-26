package org.pankuzu.test.di.jonasboner.step3

/**
  * Jonas Boner （Akka の作者）が2008年に書いた
  * "Real-World Scala: Dependency Injection (DI)"
  * （http://jonasboner.com/real-world-scala-dependency-injection-di/）
  *
  * の日本語訳より
  * http://eed3si9n.com/ja/real-world-scala-dependency-injection-di
  *
  * ver. step 3
  * ・問題点
  *   名前空間が邪魔だ（自分型アノテーションってちょっとどうなの）
  *
  */
object Boot extends App {
  val userService = ComponentRegistry.userService
  val user = userService.authenticate("olive", "pass")

}

/**
  * レジストリ・オブジェクト
  * 名前空間を統合するオブジェクト
  * サービスのインスタンス作成（と設定）をここに移動
  * => 実際のコンポーネントのインスタンスの生成とその配線を単一の構成オブジェクト（configuration object）に抽象化できた
  */
object ComponentRegistry extends UserServiceComponent with UserRepositoryComponent {
  val userRepository = new UserRepository
  val userService = new UserService
}

/**
  * UserRepository のインスタンス作成を止め、抽象メンバに変更
  */
trait UserRepositoryComponent {
  val userRepository: UserRepository
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
  * UserService のインスタンス作成を止め、抽象メンバに変更
  */
trait UserServiceComponent { this: UserRepositoryComponent =>
  val userService: UserService
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