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

package com.liferay.content.dashboard.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.content.dashboard.web.internal.configuration.FFContentDashboardConfiguration;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItem;
import com.liferay.content.dashboard.web.internal.item.selector.criteria.content.dashboard.type.criterion.ContentDashboardItemTypeItemSelectorCriterion;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemType;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemTypeFactoryTracker;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemTypeUtil;
import com.liferay.content.dashboard.web.internal.model.AssetVocabularyMetric;
import com.liferay.content.dashboard.web.internal.servlet.taglib.util.ContentDashboardDropdownItemsProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.item.selector.criteria.group.criterion.GroupItemSelectorCriterion;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.users.admin.item.selector.UserItemSelectorCriterion;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;

/**
 * @author Cristina González
 */
public class ContentDashboardAdminDisplayContext {

	public ContentDashboardAdminDisplayContext(
		List<AssetVocabulary> assetVocabularies,
		AssetVocabularyMetric assetVocabularyMetric,
		ContentDashboardDropdownItemsProvider
			contentDashboardDropdownItemsProvider,
		ContentDashboardItemTypeFactoryTracker
			contentDashboardItemTypeFactoryTracker,
		FFContentDashboardConfiguration ffContentDashboardConfiguration,
		ItemSelector itemSelector, String languageDirection,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, Portal portal,
		ResourceBundle resourceBundle,
		SearchContainer<ContentDashboardItem<?>> searchContainer) {

		_assetVocabularies = assetVocabularies;
		_assetVocabularyMetric = assetVocabularyMetric;
		_contentDashboardDropdownItemsProvider =
			contentDashboardDropdownItemsProvider;
		_contentDashboardItemTypeFactoryTracker =
			contentDashboardItemTypeFactoryTracker;
		_ffContentDashboardConfiguration = ffContentDashboardConfiguration;
		_itemSelector = itemSelector;
		_languageDirection = languageDirection;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_portal = portal;
		_resourceBundle = resourceBundle;
		_searchContainer = searchContainer;
	}

	public List<AssetVocabulary> getAssetVocabularies() {
		return _assetVocabularies;
	}

	public String getAuditGraphTitle() {
		List<String> vocabularyNames =
			_assetVocabularyMetric.getVocabularyNames();

		if (vocabularyNames.size() == 2) {
			return ResourceBundleUtil.getString(
				_resourceBundle, "content-per-x-and-x", vocabularyNames.get(0),
				vocabularyNames.get(1));
		}
		else if (vocabularyNames.size() == 1) {
			return ResourceBundleUtil.getString(
				_resourceBundle, "content-per-x", vocabularyNames.get(0));
		}
		else {
			return ResourceBundleUtil.getString(_resourceBundle, "content");
		}
	}

	public List<Long> getAuthorIds() {
		if (_authorIds != null) {
			return _authorIds;
		}

		_authorIds = Arrays.asList(
			ArrayUtil.toLongArray(
				ParamUtil.getLongValues(_liferayPortletRequest, "authorIds")));

		return _authorIds;
	}

	public String getAuthorItemSelectorURL() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(_liferayPortletRequest);

		UserItemSelectorCriterion userItemSelectorCriterion =
			new UserItemSelectorCriterion();

		userItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.singletonList(new UUIDItemSelectorReturnType()));

		PortletURL portletURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory,
			_liferayPortletResponse.getNamespace() + "selectedAuthorItem",
			userItemSelectorCriterion);

		portletURL.setParameter(
			"checkedUserIds", StringUtil.merge(getAuthorIds()));
		portletURL.setParameter(
			"checkedUserIdsEnabled", String.valueOf(Boolean.TRUE));

		return portletURL.toString();
	}

	public String getContentDashboardItemTypeItemSelectorURL() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(_liferayPortletRequest);

		ContentDashboardItemTypeItemSelectorCriterion
			contentDashboardItemTypeItemSelectorCriterion =
				new ContentDashboardItemTypeItemSelectorCriterion();

		contentDashboardItemTypeItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				Collections.singletonList(new UUIDItemSelectorReturnType()));

		PortletURL portletURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory,
			_liferayPortletResponse.getNamespace() +
				"selectedContentDashboardItemTypeItem",
			contentDashboardItemTypeItemSelectorCriterion);

		List<? extends ContentDashboardItemType> contentDashboardItemTypes =
			getContentDashboardItemTypes();

		Stream<? extends ContentDashboardItemType> stream =
			contentDashboardItemTypes.stream();

		portletURL.setParameter(
			"checkedContentDashboardItemTypes",
			stream.map(
				contentDashboardItemType ->
					contentDashboardItemType.toJSONString(
						_portal.getLocale(_liferayPortletRequest))
			).toArray(
				String[]::new
			));

		return String.valueOf(portletURL);
	}

	public List<? extends ContentDashboardItemType>
		getContentDashboardItemTypes() {

		if (_contentDashboardItemTypePayloads != null) {
			return _contentDashboardItemTypePayloads;
		}

		String[] contentDashboardItemTypePayloads =
			ParamUtil.getParameterValues(
				_liferayPortletRequest, "contentDashboardItemTypePayload",
				new String[0], false);

		if (ArrayUtil.isEmpty(contentDashboardItemTypePayloads)) {
			_contentDashboardItemTypePayloads = Collections.emptyList();
		}
		else {
			return Stream.of(
				contentDashboardItemTypePayloads
			).map(
				contentDashboardItemTypePayload ->
					ContentDashboardItemTypeUtil.
						toContentDashboardItemTypeOptional(
							_contentDashboardItemTypeFactoryTracker,
							contentDashboardItemTypePayload)
			).filter(
				Optional::isPresent
			).map(
				Optional::get
			).collect(
				Collectors.toList()
			);
		}

		return _contentDashboardItemTypePayloads;
	}

	public Map<String, Object> getData() {
		if (_data != null) {
			return _data;
		}

		_data = HashMapBuilder.<String, Object>put(
			"context", _getContext()
		).put(
			"props", _getProps()
		).build();

		return _data;
	}

	public List<DropdownItem> getDropdownItems(
		ContentDashboardItem contentDashboardItem) {

		return _contentDashboardDropdownItemsProvider.getDropdownItems(
			contentDashboardItem);
	}

	public ResourceURL getInfoPanelURL() {
		ResourceURL resourceURL = _liferayPortletResponse.createResourceURL();

		resourceURL.setResourceID("/content_dashboard/info_panel");

		return resourceURL;
	}

	public long getScopeId() {
		if (_scopeId > 0) {
			return _scopeId;
		}

		_scopeId = ParamUtil.getLong(_liferayPortletRequest, "scopeId");

		return _scopeId;
	}

	public String getScopeIdItemSelectorURL() throws PortalException {
		GroupItemSelectorCriterion groupItemSelectorCriterion =
			new GroupItemSelectorCriterion();

		groupItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new URLItemSelectorReturnType());
		groupItemSelectorCriterion.setIncludeAllVisibleGroups(true);

		return String.valueOf(
			_itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(
					_liferayPortletRequest),
				_liferayPortletResponse.getNamespace() + "selectedScopeIdItem",
				groupItemSelectorCriterion));
	}

	public SearchContainer<ContentDashboardItem<?>> getSearchContainer() {
		return _searchContainer;
	}

	public Integer getStatus() {
		if (_status != null) {
			return _status;
		}

		_status = ParamUtil.getInteger(
			_liferayPortletRequest, "status", WorkflowConstants.STATUS_ANY);

		return _status;
	}

	public long getUserId() {
		if (_userId > 0) {
			return _userId;
		}

		_userId = _portal.getUserId(_liferayPortletRequest);

		return _userId;
	}

	public boolean isAuditGraphEnabled() {
		return _ffContentDashboardConfiguration.auditGraphEnabled();
	}

	private Map<String, Object> _getContext() {
		return Collections.singletonMap(
			"languageDirection", _languageDirection);
	}

	private Map<String, Object> _getProps() {
		return Collections.singletonMap(
			"vocabularies", _assetVocabularyMetric.toJSONArray());
	}

	private final List<AssetVocabulary> _assetVocabularies;
	private final AssetVocabularyMetric _assetVocabularyMetric;
	private List<Long> _authorIds;
	private final ContentDashboardDropdownItemsProvider
		_contentDashboardDropdownItemsProvider;
	private final ContentDashboardItemTypeFactoryTracker
		_contentDashboardItemTypeFactoryTracker;
	private List<ContentDashboardItemType> _contentDashboardItemTypePayloads;
	private Map<String, Object> _data;
	private final FFContentDashboardConfiguration
		_ffContentDashboardConfiguration;
	private final ItemSelector _itemSelector;
	private final String _languageDirection;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final Portal _portal;
	private final ResourceBundle _resourceBundle;
	private long _scopeId;
	private final SearchContainer<ContentDashboardItem<?>> _searchContainer;
	private Integer _status;
	private long _userId;

}