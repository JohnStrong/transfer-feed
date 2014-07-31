package models

import io.Source
import java.net.URL

object RssStream {
	def apply(url: String) = new RssStream(new URL(url))
}

class RssStream(stream: URL) {
	// todo: allow opening of stream and parsing of contents
}