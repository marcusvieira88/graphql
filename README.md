# Introduction

This project was used for learn and pratice all the graphql features.

https://www.howtographql.com/

First install the mongodb:

https://docs.mongodb.com/manual/tutorial/install-mongodb-on-ubuntu/

For run the project you need to start the server:
```
mvn jetty:run
```
And access the url:

http://localhost:8080/

Data for test:
```
mutation {
  createLink(url:"https://marcustest12.com",description: "teste marcus12") {
   description
   url
  	id
  }
}


query {
  allLinks {
 id
  description
   url
    postedBy {
     name
     id
    }
  }
}

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

mutation {
  signinUser(
    auth: {
      email:"teste@teste.com"
    	password: "12345678"}
  ) {
  	token
    user {
     id
      name
    }
  }
}

mutation link {
  createLink(url:"https://user-logged.com",description: "user logged") {
    url
  }
}

mutation vote {
 createVote(
   linkId:"5be6adfcbc8cac17b4c4baa2"
   userId:"5be6b056bc8cac1891bae386") {

    createdAt
    link {
      id
      url
      description
    }
    user {
     id
      name
    }
  }
}

query {
  allLinks(filter: {description_contains:"marcus12",url_contains:"marcustest12"},first:2) {
  id
  description
  url
  postedBy {
   	name
    id
  	}
  }
}
```

SCHEMA file if you don't want to work with annotations:
```
type Link {
  id: ID!
  url: String!
  description: String
  postedBy: User
}

type User {
  id: ID!
  name: String!
  email: String
  password: String
}

input AuthData {
  email: String!
  password: String!
}

type SigninPayload {
  token: String
  user: User
}

type Vote {
  id: ID!
  createdAt: DateTime!
  user: User!
  link: Link!
}

scalar DateTime

type QueryResolver {
  allLinks(filter: LinkFilter, skip: Int = 0, first: Int = 0): [Link]
}

input LinkFilter {
  description_contains: String
  url_contains: String
}

type MutationResolver {
  createUser(name: String!, authProvider: AuthData!): User
  createLink(url: String!, description: String!): Link
  signinUser(auth: AuthData): SigninPayload
   createVote(linkId: ID, userId: ID): Vote
}

schema {
  query: QueryResolver,
  mutation: MutationResolver
}
```
