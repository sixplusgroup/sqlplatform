package com.example.sqlexercise.service;

/**
 * 1. @Async 的工作原理
 * 当你使用 @Async 注解标记一个方法时，Spring 会为该类创建一个代理对象。
 * 调用被 @Async 标记的方法时，实际上是通过代理对象来调用的，代理对象会将方法调用提交到线程池中异步执行。
 * 这种代理机制是基于 Spring AOP 实现的。
 * --
 * 2. 为什么不能调用同一个类中的私有方法或当前类实例的方法？
 * 2.1 不能调用同一个类中的私有方法：
 * 原因：Spring 的代理机制无法代理私有方法。
 * 私有方法只能在类内部调用，而 Spring 的代理是基于子类化（CGLIB）或接口（JDK 动态代理）实现的，无法覆盖私有方法。
 * 2.2 不能通过当前类实例调用异步方法
 * 原因：如果你在同一个类中通过 this.methodA() 调用异步方法，实际上是通过当前类实例直接调用，而不是通过代理对象调用。
 * 因此，@Async 注解不会生效。
 * --
 * 3. 解决方法
 * 将异步方法放到另一个类中，然后在当前类中通过依赖注入调用。
 * --
 * 4. 在本项目中，实现该接口的类，表明这个类是为了实现 @Async 的“另一个类”。这个接口只是一个标识，类似 RandomAccess 接口。
 */
public interface AsyncService {
}
