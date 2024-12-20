package com.example.todoapp.repositories;

import com.example.todoapp.models.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.bson.types.*;

@Repository
public interface TodoRepository extends MongoRepository<Todo, ObjectId> {

}