package org.monitor

import grails.rest.*

class AuthorController extends RestfulController {

	static responseFormats = ['json', 'xml']
	AuthorController() {
		super(Author)
		println "AuthorController......."
	}
		

	def show = {
		if(params.id && Author.exists(params.id)){
			respond Author.findById(params.id)
		}else{
			respond Author.list()
		}
	}
	
}
