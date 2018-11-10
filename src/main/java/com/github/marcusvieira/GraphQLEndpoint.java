package com.github.marcusvieira;

import com.coxautodev.graphql.tools.SchemaParser;
import javax.servlet.annotation.WebServlet;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    private static final LinkRepository linkRepository;
    private static final UserRepository userRepository;

    static {
        //Change to `new MongoClient("mongodb://<host>:<port>/graphql-java")`
        //if you don't have Mongo running locally on port 27017
        MongoDatabase mongo = new MongoClient().getDatabase("graphql-java");
        linkRepository = new LinkRepository(mongo.getCollection("links"));
        userRepository = new UserRepository(mongo.getCollection("users"));
    }

    public GraphQLEndpoint() {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema() {
        return SchemaParser.newParser()
                .file("schema.graphqls")
                .resolvers(new QueryResolver(linkRepository),
                        new MutationResolver(linkRepository, userRepository),
                        new SigninResolver())
                .build()
                .makeExecutableSchema();
    }
}