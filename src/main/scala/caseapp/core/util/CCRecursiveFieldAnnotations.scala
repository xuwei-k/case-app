package caseapp.core.util

case class CCRecursiveFieldAnnotations[A](annotations: List[(String, Either[CCRecursiveFieldAnnotations[A], List[A]])])