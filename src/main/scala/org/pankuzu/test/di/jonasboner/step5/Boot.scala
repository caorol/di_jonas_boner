package org.pankuzu.test.di.jonasboner.step5

import scaldi.{Injector, Injectable, Module}
/**
  * Jonas Boner （Akka の作者）が2008年に書いた
  * "Real-World Scala: Dependency Injection (DI)"
  * （http://jonasboner.com/real-world-scala-dependency-injection-di/）
  *
  * の日本語訳より
  * http://eed3si9n.com/ja/real-world-scala-dependency-injection-di
  *
  * ver. step 5
  * ・問題点
  *   scaldi を使って書き換えてみた
  *
  */
object Boot extends App {
  val service = new Service()(new UserModule)
  val user = service.authenticate("olive", "pass")
}

/**
  * バインディング定義
  */
class UserModule extends Module {
  bind [Repository] to new UserRepository
}

/**
  * 用途を一般化
  * 名前空間用の UserServiceComponent 削除
  * コンストラクタ引数を scaldi 形式に変更
  */
class Service(implicit inj: Injector) extends Injectable {
  val repository = inject [Repository]
  def authenticate(name: String, password: String): User ={
    repository.authenticate(new User(name, password))
  }

  def create(name: String, password: String) = {
    repository.create(new User(name, password))
  }
  def delete(user: User) = repository.delete(user)
}

/**
  *  名前空間用の UserRepositoryComponent 削除
  */
class UserRepository extends Repository {
  def authenticate(user: User): User = {
    println(s"authenticatig user: ${user.name}(${user.password})")
    user
  }

  def create(user: User) = println(s"creating user: ${user.name}(${user.password})")
  def delete(user: User) = println(s"deleting user: ${user.name}(${user.password})")
}

trait Repository {
  def authenticate(user: User): User
  def create(user: User): Unit
  def delete(user: User): Unit
}

class User(val name: String, val password: String)
