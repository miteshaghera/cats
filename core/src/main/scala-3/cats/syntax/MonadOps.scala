/*
 * Copyright (c) 2015 Typelevel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package cats.syntax

import cats.{Alternative, Monad}

final class MonadOps[F[_], A](private val fa: F[A]) extends AnyVal {
  def whileM[G[_]](using M: Monad[F], G: Alternative[G])(p: F[Boolean]): F[G[A]] = M.whileM(p)(fa)
  def whileM_(using M: Monad[F])(p: F[Boolean]): F[Unit] = M.whileM_(p)(fa)
  def untilM[G[_]](using M: Monad[F], G: Alternative[G])(p: F[Boolean]): F[G[A]] = M.untilM(fa)(p)
  def untilM_(using M: Monad[F])(p: F[Boolean]): F[Unit] = M.untilM_(fa)(p)
  def iterateWhile(using M: Monad[F])(p: A => Boolean): F[A] = M.iterateWhile(fa)(p)
  def iterateUntil(using M: Monad[F])(p: A => Boolean): F[A] = M.iterateUntil(fa)(p)
  def flatMapOrKeep[A1 >: A](using M: Monad[F])(pfa: PartialFunction[A, F[A1]]): F[A1] =
    M.flatMapOrKeep[A, A1](fa)(pfa)
}
