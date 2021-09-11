package com.tvazteca.reactiveScheduler.models.repositories;

import org.springframework.stereotype.Repository;

import com.tvazteca.reactiveScheduler.models.documents.ViewsDocument;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

@Repository
public interface ViewsRepository extends ReactiveMongoRepository <ViewsDocument, String>{
	/*List<ViewsDocument> findByContent_id(String content_id);
	/*ViewsDocument findByFecha(Date fecha);*/
	
}
