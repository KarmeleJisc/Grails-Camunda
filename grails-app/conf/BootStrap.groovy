import org.monitor.Author

class BootStrap {

    def init = { servletContext ->
		println "Application starting ... "
		
		// Check whether the test data already exists.
		if (!Author.count()) {
			def author = null
			
			println "Start loading authors into database"
			author = new Author(id:"1", name:"Adam", validated:true).save()
			assert author.save(failOnError:true, flush:true, insert: true)
			author.errors = null

			author = new Author(id:"2",name:"Mateusz", validated:false).save()
			assert author.save(failOnError:true, flush:true, insert: true)
			author.errors = null

			author = new Author(id:"3",name:"Ruben", validated:false).save()
			assert author.save(failOnError:true, flush:true, insert: true)
			author.errors = null

			assert Author.count == 3;
			println "Finished loading $Author.count authors into database"
			 
		}
    }
    def destroy = {
        println "Application shutting down... "
    }
}
