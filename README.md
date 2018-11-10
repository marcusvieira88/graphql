Introduction

This project was used for learn and pratice all the graphql features.

https://www.howtographql.com/

First install the mongodb:

https://docs.mongodb.com/manual/tutorial/install-mongodb-on-ubuntu/

For run the project you need to start the server:

mvn jetty:run

And access the url:

http://localhost:8080/

Data for test:

#mutation createLink {
#  createLink(url:"https://marcustest2.com",description: "teste marcus2") {
#    description
#  }
#}


#query {
#  allLinks {
#   description
# }
#}

mutation {
  createUser(
    name: "Marcus Vieira"
    authProvider: {
      email:"teste@teste.com"
    	password: "12345678"}) {
    id
    name
  }
}