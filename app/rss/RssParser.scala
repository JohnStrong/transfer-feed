package rss

import xml._

import models.NewsItem

trait RssParser {

	implicit def nodeToText(node:Node) = node text
	
	def parse(xml:Elem) {

		(xml \ "item").map { item =>
			val title = item \ "title" head
			val desc = item \ "description" head
			val link = item \ "link" head

			new NewsItem(title, desc, link)
		}
	}
}