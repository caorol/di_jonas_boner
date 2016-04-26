package org.pankuzu.test.di.jonasboner.step4

/**
  * Jonas Boner （Akka の作者）が2008年に書いた
  * "Real-World Scala: Dependency Injection (DI)"
  * （http://jonasboner.com/real-world-scala-dependency-injection-di/）
  *
  * の日本語訳より
  * http://eed3si9n.com/ja/real-world-scala-dependency-injection-di
  *
  * ver. step 4
  * ・問題点
  *   見慣れた形。scala っぽくはないのかな？
  *
  */
object Boot extends App {
  val userService = new UserService(new UserRepository)
  val user = userService.authenticate("olive", "pass")
}

/**
  *  名前空間用の UserRepositoryComponent 削除
  */
class UserRepository {
  def authenticate(user: User): User = {
    println(s"authenticatig user: ${user.name}(${user.password})")
    user
  }

  def create(user: User) = println(s"creating user: ${user.name}(${user.password})")
  def delete(user: User) = println(s"deleting user: ${user.name}(${user.password})")
}

/**
  * 名前空間用の UserServiceComponent 削除
  * コンストラクタ引数追加
  */
class UserService(userRepository: UserRepository) {
  def authenticate(name: String, password: String): User ={
    userRepository.authenticate(new User(name, password))
  }

  def create(name: String, password: String) = {
    userRepository.create(new User(name, password))
  }
  def delete(user: User) = userRepository.delete(user)
}

class User(val name: String, val password: String)