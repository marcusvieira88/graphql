package com.github.marcusvieira.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.github.marcusvieira.dtos.SigninPayload;
import com.github.marcusvieira.dtos.User;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;

//public class SigninResolver implements GraphQLResolver<SigninPayload> {

public class SigninResolver {

    @GraphQLQuery(name = "users")
    public User user(@GraphQLContext SigninPayload payload) {
        return payload.getUser();
    }
}
