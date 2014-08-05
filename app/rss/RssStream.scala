package rss

import io.Source
import xml.{Elem, XML}

// should extend a parser to get contents from rss
class RssStream { parser:RssParser =>

	// returns xml tree of rss feed from url source
	def read(url:String):Elem = XML.load(url)
}