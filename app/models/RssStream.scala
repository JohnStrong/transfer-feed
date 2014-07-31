package models

import io.Source
import xml.{Elem, XML}

// should extend a parser to get contents from rss
class RssStream(url: String) {
	
	// returns xml tree of rss feed from url source
	def read:Elem = XML.load(url)
}