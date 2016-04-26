package org.pankuzu.test.di.jonasboner.step1

/**
  * Jonas Boner （Akka の作者）が2008年に書いた
  * "Real-World Scala: Dependency Injection (DI)"
  * （http://jonasboner.com/real-world-scala-dependency-injection-di/）
  *
  * の日本語訳より
  * http://eed3si9n.com/ja/real-world-scala-dependency-injection-di
  *
  * ver. step 1
  * ・問題点
  *   UserService に UserRepository をインジェクトできるようにしたい
  */
object Boot extends App {
  val userService = new UserService
  userService.authenticate("olive", "password")
}

class UserRepository {
  def authenticate(user: User): User = {
    println(s"authenticatig user: ${user.name}(${user.password})")
    user
  }

  def create(user: User) = println(s"creating user: ${user.name}(${user.password})")
  def delete(user: User) = println(s"deleting user: ${user.name}(${user.password})")
}

/**
  * 以下で UserRepository のインスタンスが参照されている。
  * これがインジェクトされて欲しい依存オブジェクト。
  * => リポジトリインスタンスを UserService にインジェクトしたい
  */
class UserService {
  val userRepository = new UserRepository
  def authenticate(username: String, password: String): User ={
    userRepository.authenticate(new User(username, password))
  }

  def create(username: String, password: String) = {
    userRepository.create(new User(username, password))
  }

  def delete(user: User) = userRepository.delete(user)
}

class User(val name: String, val password: String)
