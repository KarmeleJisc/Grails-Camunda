class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
        }

		"/$controller/$id?"{
			action = [GET:"show", POST:"save", PUT:"update", DELETE:"remove"]
		}
	 
        "/"(view:"/index")
        "500"(view:'/error')
	}
}
