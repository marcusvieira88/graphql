package com.github.marcusvieira.endpoints;

import com.coxautodev.graphql.tools.SchemaParser;

import javax.management.Query;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.marcusvieira.dtos.User;
import com.github.marcusvieira.repositories.LinkRepository;
import com.github.marcusvieira.repositories.UserRepository;
import com.github.marcusvieira.repositories.VoteRepository;
import com.github.marcusvieira.resolvers.*;
import com.github.marcusvieira.utils.SanitizedError;
import com.github.marcusvieira.utils.Scalars;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLServlet;
import io.leangen.graphql.GraphQLSchemaGenerator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    private static final LinkRepository linkRepository;
    private static final UserRepository userRepository;
    private static final VoteRepository voteRepository;

    static {
        //Change to `new MongoClient("mongodb://<host>:<port>/graphql-java")`
        //if you don't have Mongo running locally on port 27017
        MongoDatabase mongo = new MongoClient().getDatabase("graphql-java");
        linkRepository = new LinkRepository(mongo.getCollection("links"));
        userRepository = new UserRepository(mongo.getCollection("users"));
        voteRepository = new VoteRepository(mongo.getCollection("votes"));
    }

    public GraphQLEndpoint() {
        super(buildSchema());
    }

    //old that used the schema.graphqls file
    //    private static GraphQLSchema buildSchema() {
    //        return SchemaParser.newParser()
    //                .file("schema.graphqls")
    //                .resolvers(new QueryResolver(linkRepository),
    //                        new MutationResolver(linkRepository, userRepository, voteRepository),
    //                        new SigninResolver(),
    //                        new LinkResolver(userRepository),
    //                        new VoteResolver(linkRepository, userRepository))
    //                .scalars(Scalars.dateTime)
    //                .build()
    //                .makeExecutableSchema();
    //    }

    //Based in ANNOTATIONS(QUERIES and MUTATIONS)
    private static GraphQLSchema buildSchema() {
        QueryResolver queryResolver = new QueryResolver(linkRepository); //create or inject the service beans
        LinkResolver linkResolver = new LinkResolver(userRepository);
        MutationResolver mutationResolver = new MutationResolver(linkRepository, userRepository, voteRepository);
        //SigninResolver signinResolver = new SigninResolver();
        VoteResolver voteResolver = new VoteResolver(linkRepository, userRepository);

        return new GraphQLSchemaGenerator()
                .withOperationsFromSingletons(queryResolver, linkResolver,
                        mutationResolver, voteResolver) //register the beans
                .generate(); //done :)
    }

    @Override
    protected GraphQLContext createContext(Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
        User user = request
                .map(req -> req.getHeader("Authorization"))
                .filter(id -> !id.isEmpty())
                .map(id -> id.replace("Bearer ", ""))
                .map(userRepository::findById)
                .orElse(null);
        return new AuthContext(user, request, response);
    }

    @Override
    protected List<GraphQLError> filterGraphQLErrors(List<GraphQLError> errors) {
        return errors.stream()
                .filter(e -> e instanceof ExceptionWhileDataFetching || super.isClientError(e))
                .map(e -> e instanceof ExceptionWhileDataFetching ? new SanitizedError((ExceptionWhileDataFetching) e) : e)
                .collect(Collectors.toList());
    }
}