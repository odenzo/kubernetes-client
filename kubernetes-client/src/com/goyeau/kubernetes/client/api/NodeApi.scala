package com.goyeau.kubernetes.client.api

import cats.effect.Sync
import com.goyeau.kubernetes.client.KubeConfig
import com.goyeau.kubernetes.client.operation._
import io.circe.{Decoder, Encoder}
import io.k8s.api.core.v1.{Node, NodeList}
import org.http4s.Uri
import org.http4s.client.Client
import org.http4s.implicits._

private[client] case class NodeApi[F[_]](httpClient: Client[F], config: KubeConfig)(implicit
    val F: Sync[F],
    val listDecoder: Decoder[NodeList],
    val resourceEncoder: Encoder[Node],
    val resourceDecoder: Decoder[Node]
) extends Creatable[F, Node]
    with Replaceable[F, Node]
    with Gettable[F, Node]
    with Listable[F, NodeList]
    with Deletable[F]
    with DeletableTerminated[F]
    with Watchable[F, Node] {
  protected val resourceUri: Uri = uri"/api" / "v1" / "nodes"
}
