package com.spring.post.springpostgdock.rest;

import com.spring.post.springpostgdock.model.Account;
import com.spring.post.springpostgdock.repository.AccountRepository;
import com.spring.post.springpostgdock.service.ServiceImpl;
import com.spring.post.springpostgdock.service.graphql.GraphQLService;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.*;
import java.util.List;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/api/books")
public class ApplicationController {

    @Autowired
    private ServiceImpl service;

    @Value("classpath:schema.graphqls")
    private Resource schemaResourse;

    private GraphQL graphQL;

//    @Autowired
//    GraphQLService graphQLService;

    @PostConstruct
    public void loadSchema() throws IOException {
        File file = schemaResourse.getFile();
        TypeDefinitionRegistry registry = new SchemaParser().parse(file);
        RuntimeWiring wiring = buildWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(registry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();

    }

    private RuntimeWiring buildWiring() {
        DataFetcher<List<Account>> fetcher1 = data ->{
            return (List<Account>) service.getAll();
        };

        DataFetcher<Account> fetcher2 = data ->{
            return (Account) service.getAccountById(data.getArgument("user_id"));
        };



        return RuntimeWiring.newRuntimeWiring().type("Query", typeWiring ->
            typeWiring.dataFetcher("allAccount", fetcher1)
                    .dataFetcher("accById", fetcher2)).build();
    }

    //get (REST)
    @GetMapping(path = "/hi", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getHello(){
        return "hi";
    }

    @GetMapping
    public List<Account> getAll(){
        return this.service.getAll();
    }

    @GetMapping(path = "/cr")
    public void create(){
        this.service.create();
    }

    @GetMapping(value = "/{id}")
    public Account getAccountById(@PathVariable("id") int id){
        return this.service.getAccountById(id);
    }

    //post (REST)
    @PostMapping
    public void save(@RequestBody Account account){
        this.service.save(account);
    }

    //delete (REST)
    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable("id") int id){
        this.service.deleteById(id);
    }

    //get (GraphQL)
    @PostMapping("/graphGetAll")
    public ResponseEntity<Object> getAllBooks(@RequestBody String query){
        ExecutionResult excute = graphQL.execute(query);

        return new ResponseEntity<Object>(excute, HttpStatus.OK);
    }

    @PostMapping("graphGetById")
    public ResponseEntity<Object> getById(@RequestBody String query){
        ExecutionResult execute = graphQL.execute(query);

        return new ResponseEntity<Object>(execute, HttpStatus.OK);
    }



}
