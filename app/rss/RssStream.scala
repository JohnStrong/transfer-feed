package rss

import io.Source
import xml.{Elem, XML}

// should extend a parser to get contents from rss
trait RssStream { parser:RssParser =>

	val link:String

	// returns xml tree of rss feed from url source
	def read:Elem = XML.load(link)
}