package com.tvazteca.reactiveScheduler.services;

import java.util.List;

import com.tvazteca.reactiveScheduler.models.documents.ViewsDocument;

public interface MetricService {

	public abstract List<ViewsDocument> getMetricByDate(String cuenta, String fecha);

}
