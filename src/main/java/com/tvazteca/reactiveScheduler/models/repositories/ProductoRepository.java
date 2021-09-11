package com.tvazteca.reactiveScheduler.models.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.tvazteca.reactiveScheduler.models.documents.Producto;

@Repository
public interface ProductoRepository extends ReactiveMongoRepository<Producto, String> {

}
