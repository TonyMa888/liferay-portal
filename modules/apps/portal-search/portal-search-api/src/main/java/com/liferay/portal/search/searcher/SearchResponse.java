/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.searcher;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.stats.StatsResponse;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Contains the full response of an executed search, as returned from the
 * search engine. 
 *
 * <p>i
 * The exact response format and the contents of the response depends on the
 * search engine and the search request that was executed.
 * </p>
 *
 * @author André de Oliveira
 * 
 */
@ProviderType
public interface SearchResponse {

	public AggregationResult getAggregationResult(String name);

	public Map<String, AggregationResult> getAggregationResultsMap();

	public List<com.liferay.portal.kernel.search.Document> getDocuments71();

	public Stream<Document> getDocumentsStream();

	public SearchRequest getRequest();

	/**
     * Returns the request string that was submitted to the search engine.
	 *
     * @return the full request string, after translation by the search engine
	 * 
	 */
	public String getRequestString();

	/**
	 * Returns the response string as returned by the search engine. Can be large
	 * depending on the number of results. Must be enabled with {@link
	 * SearchRequest#isIncludeResponseString()}.
	 *
	 * @return the response string in search engine form, or blank if disabled
	 * 
	 */
	public String getResponseString();

	public SearchHits getSearchHits();

	/**
	 * Returns the map containing the metrics aggregations computed by the
	 * search engine.
	 *
	 * @return the map containing the metrics aggregations per field
	 * 
	 */
	public Map<String, StatsResponse> getStatsResponseMap();

	public int getTotalHits();

	public void withFacetContext(Consumer<FacetContext> facetContextConsumer);

	public <T> T withFacetContextGet(
		Function<FacetContext, T> facetContextFunction);

	public void withHits(Consumer<Hits> hitsConsumer);

	public <T> T withHitsGet(Function<Hits, T> hitsFunction);

	public void withSearchContext(
		Consumer<SearchContext> searchContextConsumer);

	public <T> T withSearchContextGet(
		Function<SearchContext, T> searchContextFunction);

}
