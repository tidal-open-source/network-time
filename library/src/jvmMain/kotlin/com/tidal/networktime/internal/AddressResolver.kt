package com.tidal.networktime.internal

import java.net.Inet4Address
import java.net.Inet6Address
import java.net.InetAddress

internal actual class AddressResolver {
  actual operator fun invoke(
    address: String,
    includeIPv4: Boolean,
    includeIPv6: Boolean,
  ): Iterable<String> {
    return InetAddress.getAllByName(address)
      .mapNotNull {
        when (it) {
          is Inet4Address -> it.takeIf { includeIPv4 }
          is Inet6Address -> it.takeIf { includeIPv6 }
          else -> throw IllegalArgumentException(
            "Illegal InetAddress with type ${it.javaClass.simpleName}",
          )
        }?.hostAddress
      }
  }
}
