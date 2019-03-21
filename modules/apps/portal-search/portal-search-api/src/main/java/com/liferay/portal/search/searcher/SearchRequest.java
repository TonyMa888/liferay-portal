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

import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.stats.StatsRequest;

import java.util.List;
import java.util.Map;

/**
 * Holds the parameters used when performing a search.
 *
 <p>
 * Build the search request with the {@link com.liferay.portal.search.searcher.SearchRequestBuilder}  
 </p>
 *
 * @author André de Oliveira
 *
 */
@ProviderType
public interface SearchRequest {

	public Map<String, Aggregation> getAggregationsMap();

	public List<String> getEntryClassNames();

	public List<Class<?>> getModelIndexerClasses();

	public Map<String, PipelineAggregation> getPipelineAggregationsMap();

	public Query getQuery();

	/**
	 * Provides a secondary query to reorder the top documents returned.
	 *
	 * @return the rescore query
     *
	 */
	public Query getRescoreQuery();

	public List<Sort> getSorts();

	/**
	 * Provides the metric aggregations to be computed for each field.
	 *
	 * @return the stats that are enabled for each field.
     *
	 */
	public List<StatsRequest> getStatsRequests();

	/**
	 * Enables explanation of how each hit's score was computed.
	 *
	 * @return whether to explain scores
     *
	 */
	public boolean isExplain();

	/**
	 * Enables inclusion of the search engine's response string with the returned results.
	 *
	 * @return whether to include the response string
     *
	 */
	public boolean isIncludeResponseString();

}
